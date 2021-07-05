package com.github.shoothzj.demo.pulsar.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class TopicService {

    @SneakyThrows
    public static void createTopic(String topic, int partition) {
        PulsarUtil.pulsarAdmin().topics().createPartitionedTopic(topic, partition);
    }

}
