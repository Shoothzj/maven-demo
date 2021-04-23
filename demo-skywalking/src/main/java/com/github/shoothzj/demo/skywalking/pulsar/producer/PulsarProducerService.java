package com.github.shoothzj.demo.skywalking.pulsar.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Producer;

/**
 * @author hezhangjian
 */
@Slf4j
public class PulsarProducerService {

    static volatile Producer<String> pulsarProducer;

    public static void send(String msg) {
        if (pulsarProducer == null) {
            pulsarProducer = PulsarProducerMain.createProducer();
        }
        PulsarProducerMain.sendRecord(pulsarProducer);
    }


}
