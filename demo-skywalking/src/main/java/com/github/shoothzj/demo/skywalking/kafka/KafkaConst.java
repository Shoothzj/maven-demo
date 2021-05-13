package com.github.shoothzj.demo.skywalking.kafka;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class KafkaConst {

    public static String KAFKA_BROKERS = "localhost:9092";

    public static String CLIENT_ID = "client1";

    public static String TOPIC_NAME = "demo";

    public static String GROUP_ID_CONFIG = "consumerGroup1";

    public static String OFFSET_RESET_LATEST = "latest";

    public static String OFFSET_RESET_EARLIER = "earliest";

    public static Integer MAX_POLL_RECORDS = 1;

}