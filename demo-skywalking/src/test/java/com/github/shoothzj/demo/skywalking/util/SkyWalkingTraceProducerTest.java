package com.github.shoothzj.demo.skywalking.util;

import com.github.shoothzj.demo.kafka.KafkaProducerFactory;
import com.github.shoothzj.demo.skywalking.SkyWalkingConst;
import com.github.shoothzj.javatool.util.CommonUtil;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.skywalking.apm.network.language.agent.v3.SegmentObject;
import org.apache.skywalking.apm.network.language.agent.v3.SpanObject;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author hezhangjian
 */
public class SkyWalkingTraceProducerTest {

    @Test
    public void produce() {
        String instanceId = "xxxxxxxxxx@172.16.2.2";
        String service = "Ghuow";
        Producer<String, byte[]> producer = KafkaProducerFactory.createByteProducer();
        while (true) {
            String traceId = GlobalIdGenerator.generate();
            String traceSegmentId = GlobalIdGenerator.generate();

            SpanObject spanObject = SpanObject.newBuilder().setSpanId(0).setParentSpanId(-1)
                    .setStartTime(System.currentTimeMillis() - 199).setEndTime(System.currentTimeMillis())
                    .setOperationName("Hello").setPeer("").build();
            SegmentObject segmentObject = SegmentObject.newBuilder().setTraceId(traceId).setTraceSegmentId(traceSegmentId).setService(service)
                    .setServiceInstance(instanceId).addSpans(spanObject).build();
            producer.send(new ProducerRecord<>(SkyWalkingConst.TRACE_TOPIC, instanceId, segmentObject.toByteArray()));
            CommonUtil.sleep(TimeUnit.SECONDS, 10);
        }
    }

}
