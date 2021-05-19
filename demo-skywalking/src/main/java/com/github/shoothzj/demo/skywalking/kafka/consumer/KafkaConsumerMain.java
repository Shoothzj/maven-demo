package com.github.shoothzj.demo.skywalking.kafka.consumer;

import com.github.shoothzj.demo.kafka.KafkaConst;
import com.github.shoothzj.demo.skywalking.util.SkyWalking8Util;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @author hezhangjian
 */
@Slf4j
public class KafkaConsumerMain {

    public static Consumer<Long, String> createConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConst.BROKERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "consumerGroup1");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, KafkaConst.OFFSET_RESET_EARLIER);

        Consumer<Long, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(KafkaConst.DEMO_TOPIC));
        return consumer;
    }

    @SneakyThrows
    public static void main(String[] args) {
        final Consumer<Long, String> consumer = createConsumer();
        while (true) {
            final ConsumerRecords<Long, String> consumerRecords = consumer.poll(Duration.ofMillis(500));
            if (consumerRecords.isEmpty()) {
                continue;
            }
            for (ConsumerRecord<Long, String> consumerRecord : consumerRecords) {
                final Headers headers = consumerRecord.headers();
                {
                    final Header lastHeader = headers.lastHeader("sw8");
                    try {
                        SkyWalking8Util.parseSw(lastHeader.value());
                    } catch (Exception e) {
                        log.error("exception data is {} ex {}", lastHeader, e);
                    }
                }
                {
                    final Header lastHeader = headers.lastHeader("sw8-x");
                    log.info("header {}", lastHeader);
                    SkyWalking8Util.parseSwX(lastHeader.value());
                }
                {
                    // correlation暂时没用到
                    final Header lastHeader = headers.lastHeader("sw8-correlation");
                    SkyWalking8Util.parseSwCorr(lastHeader.value());
                }
                log.info("offset is {}", consumerRecord.offset());
            }
        }
    }

}
