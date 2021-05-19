package com.github.shoothzj.demo.skywalking.kafka.producer;

import com.github.shoothzj.demo.kafka.KafkaConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author hezhangjian
 */
@Slf4j
public class KafkaProducerService {

    static volatile Producer<Long, String> kafkaProducer;

    public static void send(String msg) {
        if (kafkaProducer == null) {
            kafkaProducer = createProducer();
        }
        sendRecord(kafkaProducer, msg);
    }

    public static Producer<Long, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConst.BROKERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "client1");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }

    public static void sendRecord(Producer<Long, String> producer, String msg) {
        final ProducerRecord<Long, String> producerRecord = new ProducerRecord<>(KafkaConst.DEMO_TOPIC, 123L, msg);
        producer.send(producerRecord);
    }

}
