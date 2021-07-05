package com.github.shoothzj.demo.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

/**
 * @author hezhangjian
 */
@Slf4j
public class KafkaConsumerFactory {

    public static Consumer<String, String> createStringConsumer(String topic) {
        return createStringConsumer(topic, UUID.randomUUID().toString(), KafkaConst.OFFSET_RESET_LATEST);
    }

    public static Consumer<String, byte[]> createByteConsumer(String topic) {
        return createByteConsumer(topic, UUID.randomUUID().toString(), KafkaConst.OFFSET_RESET_LATEST);
    }

    public static Consumer<String, String> createRewindStringConsumer(String topic) {
        return createStringConsumer(topic, UUID.randomUUID().toString(), KafkaConst.OFFSET_RESET_EARLIER);
    }

    public static Consumer<String, byte[]> createRewindByteConsumer(String topic) {
        return createByteConsumer(topic, UUID.randomUUID().toString(), KafkaConst.OFFSET_RESET_EARLIER);
    }

    private static Consumer<String, String> createStringConsumer(String topic, String groupId, String front) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConst.BROKERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, front);

        Consumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));
        return consumer;
    }

    private static Consumer<String, byte[]> createByteConsumer(String topic, String groupId, String front) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConst.BROKERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, front);

        Consumer<String, byte[]> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));
        return consumer;
    }

}
