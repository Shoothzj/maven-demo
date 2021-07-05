package com.github.shoothzj.demo.kafka;

import com.github.shoothzj.javatool.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.Test;

import java.time.Duration;

/**
 * @author hezhangjian
 */
@Slf4j
public class KafkaConsumerTest {

    @Test
    public void createKafkaConsumerOnce() {
        LogUtil.configureLog();
        String topic = "764edee3-007e-48e0-b9f9-df7f713ff707";
        final Consumer<String, String> consumer = KafkaConsumerFactory.createStringConsumer(topic);
        final ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(500));
        for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
            log.info("consumer record {} key {}", consumerRecord.offset(), consumerRecord.key());
        }
        consumer.close();
    }

}
