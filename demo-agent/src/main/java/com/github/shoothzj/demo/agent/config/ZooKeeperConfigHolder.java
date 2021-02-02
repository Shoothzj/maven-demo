package com.github.shoothzj.demo.agent.config;

/**
 * @author hezhangjian
 */
public class ZooKeeperConfigHolder {

    /**
     * true
     * false
     */
    public static volatile String writable;

    public static boolean isWritable() {
        if (writable == null) {
            return true;
        }
        return "true".equals(writable);
    }

}
