package com.github.shoothzj.demo.kafka;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class KafkaConst {

    public static String BROKERS = "localhost:9092";

    public static String OFFSET_RESET_LATEST = "latest";

    public static String OFFSET_RESET_EARLIER = "earliest";

    public static String DEMO_TOPIC = "demo";


}
