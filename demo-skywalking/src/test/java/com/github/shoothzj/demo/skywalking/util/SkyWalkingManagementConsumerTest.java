package com.github.shoothzj.demo.skywalking.util;

import com.github.shoothzj.demo.kafka.KafkaConsumerFactory;
import com.github.shoothzj.demo.skywalking.SkyWalkingConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.skywalking.apm.network.management.v3.InstancePingPkg;
import org.apache.skywalking.apm.network.management.v3.InstanceProperties;
import org.junit.Test;

import java.time.Duration;

/**
 * @author hezhangjian
 */
@Slf4j
public class SkyWalkingManagementConsumerTest {

    @Test
    public void consumeManagements() throws Exception {
        Consumer<String, byte[]> consumer = KafkaConsumerFactory.createRewindByteConsumer(SkyWalkingConst.MANAGEMENT_TOPIC);
        while (true) {
            final ConsumerRecords<String, byte[]> consumerRecords = consumer.poll(Duration.ofMillis(500));
            if (consumerRecords.isEmpty()) {
                continue;
            }
            for (ConsumerRecord<String, byte[]> record : consumerRecords) {
                byte[] value = record.value();
                if (record.key().startsWith("register-")) {
                    //5分钟一次
                    /**
                     * serviceInstance: "f87c2bd7c70f40909c0af5276bc6c07c@192.168.1.107"
                     * properties {
                     *   key: "OS Name"
                     *   value: "Windows 10"
                     * }
                     * properties {
                     *   key: "hostname"
                     *   value: "DESKTOP-LJG3A0S"
                     * }
                     * properties {
                     *   key: "ipv4"
                     *   value: "192.168.1.107"
                     * }
                     * properties {
                     *   key: "ipv4"
                     *   value: "172.28.176.1"
                     * }
                     * properties {
                     *   key: "Process No."
                     *   value: "25872"
                     * }
                     * properties {
                     *   key: "language"
                     *   value: "java"
                     * }
                     */
                    InstanceProperties instanceProperties = InstanceProperties.parseFrom(value);
                    log.info("ts {} instance {}", record.timestamp(), instanceProperties);
                } else {
                    // 30s一次
                    InstancePingPkg instancePingPkg = InstancePingPkg.parseFrom(record.value());
                    log.info("ts {} ping pkg {}", record.timestamp(), instancePingPkg);
                }
            }
        }
    }

}
