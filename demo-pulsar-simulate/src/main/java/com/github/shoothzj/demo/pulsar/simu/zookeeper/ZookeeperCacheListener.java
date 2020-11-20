package com.github.shoothzj.demo.pulsar.simu.zookeeper;

import org.apache.zookeeper.data.Stat;

/**
 * @author hezhangjian
 */
public interface ZookeeperCacheListener<T> {

    void update(String path, T data, Stat stat);

}
