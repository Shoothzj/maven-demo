package com.github.shoothzj.demo.zookeeper;

import com.github.shoothzj.javatool.util.CommonUtil;
import com.github.shoothzj.javatool.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.TimeUnit;

/**
 * @author hezhangjian
 */
@Slf4j
public class TempOrderTest3 {

    public static void main(String[] args) throws Exception {
        LogUtil.configureLog();
        RetryPolicy retryPolicy = new RetryNTimes(3, 5000);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
                .sessionTimeoutMs(10000).retryPolicy(retryPolicy).build();
        client.start();
        String path = client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/temp/seqX", "World".getBytes());
        log.info("path is [{}]", path);
        CommonUtil.sleep(TimeUnit.HOURS, 1);
    }

}
