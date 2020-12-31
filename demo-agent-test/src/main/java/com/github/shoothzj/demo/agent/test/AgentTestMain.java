package com.github.shoothzj.demo.agent.test;

import com.github.shoothzj.javatool.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hezhangjian
 */
@Slf4j
@SpringBootApplication
public class AgentTestMain {

    public static void main(String[] args) {
        LogUtil.configureLog();
        SpringApplication.run(AgentTestMain.class);
    }

}
