package com.github.shoothzj.demo.pulsar.simu.zookeeper;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.shoothzj.demo.pulsar.simu.common.util.FutureUtil;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.bookkeeper.common.util.OrderedExecutor;
import org.apache.bookkeeper.common.util.SafeRunnable;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author hezhangjian
 */
@Slf4j
public abstract class ZooKeeperCache implements Watcher {

    public interface Deserializer<T> {
        T deserialize(String key, byte[] content) throws Exception;
    }

    public interface CacheUpdater<T> {

        void registerListener(ZookeeperCacheListener<T> listener);

        void unregisterListener(ZookeeperCacheListener<T> listener);

        void reloadCache(String path);

    }

    public static final String ZK_CACHE_INSTANCE = "zk_cache_instance";

    protected final AsyncLoadingCache<String, Pair<Map.Entry<Object, Stat>, Long>> dataCache;

    protected final AsyncLoadingCache<String, Set<String>> childrenCache;

    protected final AsyncLoadingCache<String, Boolean> existsCache;

    private final OrderedExecutor executor;

    private final OrderedExecutor backgroundExecutor = OrderedExecutor.newBuilder().name("zk-cache-background").numThreads(2).build();

    private boolean shouldShutdownExecutor;

    private final int zkOperationTimeoutSeconds;

    private static final int DEFAULT_CACHE_EXPIRY_SECONDS = 300;

    private final int cacheExpirySeconds;

    protected AtomicReference<ZooKeeper> zkSession = new AtomicReference<>(null);

    public ZooKeeperCache(String cacheName, ZooKeeper zkSession, int zkOperationTimeoutSeconds) {
        this(cacheName, zkSession, zkOperationTimeoutSeconds,
                OrderedExecutor.newBuilder().name("zk-cache-callback-executor").build());
        this.shouldShutdownExecutor = true;
    }

    public ZooKeeperCache(String cacheName, ZooKeeper zkSession, int zkOperationTimeoutSeconds, OrderedExecutor executor) {
        this(cacheName, zkSession, zkOperationTimeoutSeconds, executor, DEFAULT_CACHE_EXPIRY_SECONDS);
    }

    public ZooKeeperCache(String cacheName, ZooKeeper zkSession, int zkOperationTimeoutSeconds,
                          OrderedExecutor executor, int cacheExpirySeconds) {
        Preconditions.checkNotNull(executor);
        this.zkOperationTimeoutSeconds = zkOperationTimeoutSeconds;
        this.executor = executor;
        this.zkSession.set(zkSession);
        this.shouldShutdownExecutor = false;
        this.cacheExpirySeconds = cacheExpirySeconds;

        this.dataCache = Caffeine.newBuilder()
                .recordStats()
                .buildAsync((key, executor1) -> null);
        this.childrenCache = Caffeine.newBuilder()
                .recordStats()
                .expireAfterWrite(cacheExpirySeconds, TimeUnit.SECONDS)
                .buildAsync((key, executor1) -> null);
        this.existsCache = Caffeine.newBuilder()
                .recordStats()
                .expireAfterWrite(cacheExpirySeconds, TimeUnit.SECONDS)
                .buildAsync((key, executor1) -> null);


    }

    public ZooKeeper getZooKeeper() {
        return this.zkSession.get();
    }

    public <T> void process(WatchedEvent event, final CacheUpdater<T> updater) {
        final String path = event.getPath();
        if (path != null) {
            dataCache.synchronous().invalidate(path);
            childrenCache.synchronous().invalidate(path);
            // invalidate cache for parent if child is created/deleted
            if (event.getType().equals(Event.EventType.NodeCreated) || event.getType().equals(Event.EventType.NodeDeleted)) {
                childrenCache.synchronous().invalidate(ZkUtils.getParentForPath(path));
            }
            existsCache.synchronous().invalidate(path);
        }
        if (executor != null && updater != null) {
            if (log.isDebugEnabled()) {
                log.debug("submitting reload cache task to the executor for path: {}, updater: {}", path, updater);
            }
            try {
                executor.executeOrdered(path, new SafeRunnable() {
                    @Override
                    public void safeRun() {
                        updater.reloadCache(path);
                    }
                });
            } catch (RejectedExecutionException e) {
                // Ok, the service is shutting down
                log.error("Failed to updated zk-cache {} on zk-watch {}", path, e.getMessage());
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Cannot reload cache for path: {}, updater: {}", path, updater);
            }
        }
    }


    public void invalidateAll() {
        invalidateAllData();
        invalidateAllChildren();
        invalidateAllExists();
    }

    private void invalidateAllExists() {
        existsCache.synchronous().invalidateAll();
    }

    public void invalidateAllData() {
        dataCache.synchronous().invalidateAll();
    }

    public void invalidateAllChildren() {
        childrenCache.synchronous().invalidateAll();
    }

    public void invalidateData(String path) {
        dataCache.synchronous().invalidate(path);
    }

    public void invalidateChildren(String path) {
        childrenCache.synchronous().invalidate(path);
    }

    private void invalidateExists(String path) {
        existsCache.synchronous().invalidate(path);
    }

    public void asyncInvalidate(String path) {
        backgroundExecutor.execute(() -> invalidate(path));
    }

    public int getZkOperationTimeoutSeconds() {
        return zkOperationTimeoutSeconds;
    }

    public void invalidate(final String path) {
        invalidateData(path);
        invalidateChildren(path);
        invalidateExists(path);
    }

    /**
     * Returns if the node at the given path exists in the cache
     *
     * @param path
     *            path of the node
     * @return true if node exists, false if it does not
     * @throws KeeperException
     * @throws InterruptedException
     */
    public boolean exists(final String path) throws KeeperException, InterruptedException {
        return exists(path, this);
    }

    private boolean exists(final String path, Watcher watcher) throws KeeperException, InterruptedException {
        return existsAsync(path, watcher).join();
    }

    public CompletableFuture<Boolean> existsAsync(String path, Watcher watcher) {
        return existsCache.get(path, (p, executor) -> {
            ZooKeeper zk = zkSession.get();
            if (zk == null) {
                return FutureUtil.failedFuture(new IOException("ZK session not ready"));
            }

            CompletableFuture<Boolean> future = new CompletableFuture<>();
            zk.exists(path, watcher, (rc, path1, ctx, stat) -> {
                if (rc == KeeperException.Code.OK.intValue()) {
                    future.complete(true);
                } else if (rc == KeeperException.Code.NONODE.intValue()) {
                    future.complete(false);
                } else {
                    future.completeExceptionally(KeeperException.create(KeeperException.Code.get(rc)));
                }
            }, null);

            return future;
        });
    }



}
