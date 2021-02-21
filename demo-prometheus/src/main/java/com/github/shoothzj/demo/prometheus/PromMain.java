package com.github.shoothzj.demo.prometheus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author hezhangjian
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
public class PromMain {

    public static void main(String[] args) {
        SpringApplication.run(PromMain.class);
    }

}
