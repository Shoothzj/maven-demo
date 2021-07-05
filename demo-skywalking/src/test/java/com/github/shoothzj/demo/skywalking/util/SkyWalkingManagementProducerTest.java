package com.github.shoothzj.demo.skywalking.util;

import com.github.shoothzj.demo.kafka.KafkaProducerFactory;
import com.github.shoothzj.demo.skywalking.SkyWalkingConst;
import com.github.shoothzj.javatool.util.CommonUtil;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.skywalking.apm.network.common.v3.KeyStringValuePair;
import org.apache.skywalking.apm.network.management.v3.InstancePingPkg;
import org.apache.skywalking.apm.network.management.v3.InstanceProperties;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class SkyWalkingManagementProducerTest {

    private static final String ip = "127.0.0.1";

    @Test
    public void produce() {
        String instanceId = "xxxxxxxxxx@" + ip;
        String service = "Ghuow";
        Producer<String, byte[]> producer = KafkaProducerFactory.createByteProducer();;
        long aux = 0;
        while (true) {
            if (aux % 5 == 0) {
                // send reg
                KeyStringValuePair osValuePair = KeyStringValuePair.newBuilder().setKey("OS Name").setValue("Windows 10").build();
                KeyStringValuePair hostNamePair = KeyStringValuePair.newBuilder().setKey("hostname").setValue("DDDD").build();
                KeyStringValuePair ipPair = KeyStringValuePair.newBuilder().setKey("ipv4").setValue(ip).build();
                KeyStringValuePair processPair = KeyStringValuePair.newBuilder().setKey("Process No.").setValue("2661").build();
                KeyStringValuePair languagePair = KeyStringValuePair.newBuilder().setKey("language").setValue("java").build();
                InstanceProperties.Builder serviceBuilder = InstanceProperties.newBuilder().setServiceInstance(instanceId)
                        .setService(service);
                InstanceProperties.Builder builder = serviceBuilder.addProperties(osValuePair).addProperties(hostNamePair)
                        .addProperties(ipPair).addProperties(processPair).addProperties(languagePair);
                InstanceProperties properties = builder.build();
                producer.send(new ProducerRecord<>(SkyWalkingConst.MANAGEMENT_TOPIC, "register-" + instanceId, properties.toByteArray()));
            }
            // send ping
            InstancePingPkg instancePingPkg = InstancePingPkg.newBuilder().setServiceInstance(instanceId).setService(service).build();
            producer.send(new ProducerRecord<>(SkyWalkingConst.MANAGEMENT_TOPIC, instanceId, instancePingPkg.toByteArray()));
            CommonUtil.sleep(TimeUnit.SECONDS, 30);
            aux++;
        }
    }

}
