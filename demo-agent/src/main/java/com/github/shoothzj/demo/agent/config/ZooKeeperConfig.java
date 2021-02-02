package com.github.shoothzj.demo.agent.config;

import com.github.shoothzj.demo.agent.util.AgentUtil;
import com.github.shoothzj.demo.agent.util.TimerUtil;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author hezhangjian
 */
public class ZooKeeperConfig {

    static {
        TimerUtil.getScheduler().scheduleWithFixedDelay(ZooKeeperConfig::refreshConfig, 1, 1, TimeUnit.MINUTES);
    }

    public static void refreshConfig() {
        final String property = System.getProperty("user.home");
        final String file = property + "/zookeeper.properties";
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            final Properties properties = new Properties();
            properties.load(fileInputStream);
            ZooKeeperConfigHolder.writable = properties.getProperty("writable");
        } catch (Exception e) {
            AgentUtil.error("exception ", e);
        }
    }

}
