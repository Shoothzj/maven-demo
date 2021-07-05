package com.github.shoothzj.demo.kafka;

import com.github.shoothzj.javatool.util.CommonUtil;
import com.github.shoothzj.javatool.util.LogUtil;
import com.github.shoothzj.javatool.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.Test;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author hezhangjian
 */
@Slf4j
public class KafkaIntegrateTest {

    @Test
    public void testKafkaIntegrate() {
        LogUtil.configureLog();
        String topic = UUID.randomUUID().toString();
        final Producer<String, String> producer = KafkaProducerFactory.createStringProducer();
        final Consumer<String, String> consumer = KafkaConsumerFactory.createStringConsumer(topic);
        new Thread(() -> {
            CommonUtil.sleep(TimeUnit.SECONDS, 5);
            producer.send(RecordUtil.randomStringRecord(topic), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    log.info("metadata is {}", metadata.offset());
                }
            });
        }).start();
        while (true) {
            final ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(500));
            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                log.info("consumer record {} key {}", consumerRecord.offset(), consumerRecord.key());
            }
        }
    }

}
