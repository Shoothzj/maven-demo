package com.github.shoothzj.demo.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.UUID;

/**
 * @author hezhangjian
 */
@Slf4j
public class RecordUtil {

    public static ProducerRecord<String, String> randomStringRecord(String topic) {
        return new ProducerRecord<String, String>(topic, UUID.randomUUID().toString());
    }

}
