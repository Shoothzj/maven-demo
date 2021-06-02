package com.github.shoothzj.demo.avro;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class PulsarInstanceCodecTest {

    private final PulsarInstanceCodec pulsarInstanceCodec = new PulsarInstanceCodec();

    @Test
    public void serializeToJson() {
        final PulsarInstance pulsarInstance = new PulsarInstance();
        pulsarInstance.setCluster("default");
        pulsarInstance.setUrl("pulsar.com");
        final byte[] bytes = pulsarInstanceCodec.serializeJson(pulsarInstance);
        log.info("result is {}", new String(bytes));
    }

    @Test
    public void serializeToBinary() {
        final PulsarInstance pulsarInstance = new PulsarInstance();
        pulsarInstance.setCluster("default");
        pulsarInstance.setUrl("pulsar.com");
        final byte[] bytes = pulsarInstanceCodec.serializeBinary(pulsarInstance);
        log.info("result is {}", bytes.length);
    }

}