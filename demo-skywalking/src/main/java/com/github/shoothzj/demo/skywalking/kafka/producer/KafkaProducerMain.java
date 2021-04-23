package com.github.shoothzj.demo.skywalking.kafka.producer;

import com.github.shoothzj.demo.skywalking.kafka.KafkaConst;
import com.github.shoothzj.javatool.util.CommonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author hezhangjian
 */
@Slf4j
public class KafkaProducerMain {

    public static Producer<Long, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConst.KAFKA_BROKERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, KafkaConst.CLIENT_ID);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }

    public static void sendRecord(Producer<Long, String> producer) {
        final ProducerRecord<Long, String> producerRecord = new ProducerRecord<>(KafkaConst.TOPIC_NAME, 123L, "111");
        producer.send(producerRecord);
    }

    @SneakyThrows
    public static void main(String[] args) {
        final Producer<Long, String> producer = createProducer();
        while (true) {
            sendRecord(producer);
            CommonUtil.sleep(TimeUnit.SECONDS, 1);
        }
    }

}
