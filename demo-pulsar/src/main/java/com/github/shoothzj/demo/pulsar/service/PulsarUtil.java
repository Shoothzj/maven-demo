package com.github.shoothzj.demo.pulsar.service;

import com.github.shoothzj.demo.pulsar.PulsarConstant;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.api.PulsarClient;

/**
 * @author hezhangjian
 */
@Slf4j
public class PulsarUtil {

    @SneakyThrows
    public static PulsarAdmin pulsarAdmin() {
        return PulsarAdmin.builder().serviceHttpUrl(PulsarConstant.SERVICE_HTTP_URL).build();
    }

    @SneakyThrows
    public static PulsarClient pulsarClient() {
        return PulsarClient.builder().serviceUrl(PulsarConstant.SERVICE_HTTP_URL).build();
    }

}
