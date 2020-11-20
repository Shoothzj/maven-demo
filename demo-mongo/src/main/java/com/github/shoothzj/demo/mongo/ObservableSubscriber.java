package com.github.shoothzj.demo.mongo;

import com.mongodb.MongoTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author hezhangjian
 */
@Slf4j
public class ObservableSubscriber implements Subscriber {

    private final List received;

    private final List<Throwable> errors;

    private final CountDownLatch latch;

    private volatile Subscription subscription;

    private volatile boolean completed;

    public ObservableSubscriber() {
        this.received = new ArrayList();
        this.errors = new ArrayList();
        this.latch = new CountDownLatch(1);
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void onNext(Object o) {
        received.add(o);
    }

    @Override
    public void onError(Throwable throwable) {
        errors.add(throwable);
        onComplete();
    }

    @Override
    public void onComplete() {
        completed = true;
        latch.countDown();
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public List getReceived() {
        return received;
    }

    public Throwable getError() {
        if (errors.size() > 0) {
            return errors.get(0);
        }
        return null;
    }

    public boolean isCompleted() {
        return completed;
    }

    /**
     * 阻塞一定时间等待结果
     *
     * @param timeout
     * @param unit
     * @return
     * @throws Throwable
     */
    public List get(final long timeout, final TimeUnit unit) throws Throwable {
        return await(timeout, unit).getReceived();
    }

    /**
     * 一直阻塞等待请求完成
     *
     * @return
     * @throws Throwable
     */
    public ObservableSubscriber await() throws Throwable {
        return await(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }

    /**
     * 阻塞一定时间等待完成
     *
     * @param timeout
     * @param unit
     * @return
     * @throws Throwable
     */
    public ObservableSubscriber await(final long timeout, final TimeUnit unit) throws Throwable {
        subscription.request(Integer.MAX_VALUE);
        if (!latch.await(timeout, unit)) {
            throw new MongoTimeoutException("Publisher onComplete timed out");
        }
        if (!errors.isEmpty()) {
            throw errors.get(0);
        }
        return this;
    }
}
