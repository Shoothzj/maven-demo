package com.github.shoothzj.demo.agent.util;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hezhangjian
 */
public class ZooKeeperUtil {

    static final ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);

    static {
        scheduledExecutorService.scheduleWithFixedDelay(ZooKeeperUtil::refreshConfig, 1, 1, TimeUnit.MINUTES);
    }

    static volatile boolean val = false;

    public static void refreshConfig() {
        try {
            final String property = System.getProperty("user.home");
            final String file = property + "/zookeeper.config";
            if (FileUtil.readFile(file) != null) {
                val = true;
            } else {
                val = false;
            }
        } catch (Exception e) {
            AgentUtil.error("exception ", e);
        }
    }

    public static boolean getWriteError() {
        return val;
    }

}
