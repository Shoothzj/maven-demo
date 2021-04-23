package com.github.shoothzj.demo.skywalking.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;

/**
 * @author hezhangjian
 */
@Slf4j
public class KafkaProducerService {

    static volatile Producer<Long, String> kafkaProducer;

    public static void send(String msg) {
        if (kafkaProducer == null) {
            kafkaProducer = KafkaProducerMain.createProducer();
        }
        KafkaProducerMain.sendRecord(kafkaProducer);
    }

}
