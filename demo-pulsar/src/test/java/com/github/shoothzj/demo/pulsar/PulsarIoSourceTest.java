package com.github.shoothzj.demo.pulsar;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.internal.PulsarAdminBuilderImpl;
import org.apache.pulsar.common.io.SinkConfig;
import org.junit.Test;

import java.util.HashMap;

public class PulsarIoSourceTest {

    /**
     * configs:
     *     bootstrapServers: "localhost:6667"
     *     topic: "test"
     *     acks: "1"
     *     batchSize: "16384"
     *     maxRequestSize: "1048576"
     *     producerConfigProperties:
     *         client.id: "test-pulsar-producer"
     *         security.protocol: "SASL_PLAINTEXT"
     *         sasl.mechanism: "GSSAPI"
     *         sasl.kerberos.service.name: "kafka"
     *         acks: "all"
     * @throws Exception
     */
    @Test
    public void createKafkaSource() throws Exception {
        final PulsarAdmin pulsarAdmin = new PulsarAdminBuilderImpl().build();
        final SinkConfig sinkConfig = new SinkConfig();
        //sink configs
        {
            final HashMap<String, Object> hashMap = new HashMap<>();
            sinkConfig.setConfigs(hashMap);
        }
        pulsarAdmin.sinks().createSink(sinkConfig, "");
    }

}