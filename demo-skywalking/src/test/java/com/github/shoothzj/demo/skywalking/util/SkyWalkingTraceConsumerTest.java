package com.github.shoothzj.demo.skywalking.util;

import com.github.shoothzj.demo.kafka.KafkaConsumerFactory;
import com.github.shoothzj.demo.skywalking.SkyWalkingConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.skywalking.apm.network.language.agent.v3.SegmentObject;
import org.junit.Test;

import java.time.Duration;

@Slf4j
public class SkyWalkingTraceConsumerTest {

    @Test
    public void consumeTrace() throws Exception {
        Consumer<String, byte[]> consumer = KafkaConsumerFactory.createRewindByteConsumer(SkyWalkingConst.TRACE_TOPIC);
        while (true) {
            final ConsumerRecords<String, byte[]> consumerRecords = consumer.poll(Duration.ofMillis(500));
            if (consumerRecords.isEmpty()) {
                continue;
            }
            for (ConsumerRecord<String, byte[]> record : consumerRecords) {
                byte[] value = record.value();
                SegmentObject segmentObject = SegmentObject.parseFrom(value);
                log.info("segment obj is {}", segmentObject);
            }
        }
    }

}
