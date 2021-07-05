package com.github.shoothzj.demo.pulsar;

import com.github.shoothzj.demo.pulsar.service.PulsarUtil;
import com.github.shoothzj.demo.pulsar.service.TopicService;
import com.github.shoothzj.javatool.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.ConsumerBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author hezhangjian
 */
@Slf4j
public class PulsarMutliConsumerMemoryLeak {

    public static void main(String[] args) throws Exception {
        TopicService.createTopic("test-multi-topic", 2);
        final ConsumerBuilder<byte[]> consumerBuilder = PulsarUtil.pulsarClient().newConsumer();
        final Consumer<byte[]> consumer = consumerBuilder.topic("test-multi").subscriptionName("sub-1").subscribe();
        consumer.unsubscribe();
        CommonUtil.sleep(TimeUnit.DAYS, 1);
    }

}
