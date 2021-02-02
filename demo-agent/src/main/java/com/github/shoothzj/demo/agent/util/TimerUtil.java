package com.github.shoothzj.demo.agent.util;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author hezhangjian
 */
public class TimerUtil {

    private static final ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);

    public static ScheduledExecutorService getScheduler() {
        return scheduler;
    }
}
