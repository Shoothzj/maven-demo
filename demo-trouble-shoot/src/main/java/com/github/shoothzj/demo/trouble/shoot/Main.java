package com.github.shoothzj.demo.trouble.shoot;

import lombok.extern.slf4j.Slf4j;
import org.apache.bookkeeper.mledger.proto.MLDataFormats;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author hezhangjian
 */
@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        final CuratorFramework framework = CuratorFrameworkFactory.newClient("127.0.0.1", retryPolicy);
        final byte[] bytes = framework.getData().forPath("");
        System.out.println(MLDataFormats.ManagedLedgerInfo.parseFrom(bytes));
    }

}
