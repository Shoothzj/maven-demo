package com.github.shoothzj.demo.skywalking.kafka.producer;

import com.github.shoothzj.javatool.util.CommonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author hezhangjian
 */
@Slf4j
public class KafkaProducerMain {

    @SneakyThrows
    public static void main(String[] args) {
        while (true) {
            KafkaProducerService.send("msg1111");
            CommonUtil.sleep(TimeUnit.SECONDS, 1);
        }
    }

}
