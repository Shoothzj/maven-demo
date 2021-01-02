package com.github.shoothzj.demo.iot.mdevice.manage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author hezhangjian
 */
@Slf4j
@EnableJpaAuditing
@EnableJpaRepositories({"com.github.shoothzj.demo.iot.mdevice.manage.repository"})
@EntityScan(basePackages = {"com.github.shoothzj.demo.iot.mdevice.manage.domain"})
@SpringBootApplication
public class MysqlDemo {

    public static void main(String[] args) {
        SpringApplication.run(MysqlDemo.class);
    }

}
