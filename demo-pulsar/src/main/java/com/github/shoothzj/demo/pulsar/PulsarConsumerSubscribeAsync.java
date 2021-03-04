package com.github.shoothzj.demo.pulsar;

import com.github.shoothzj.javatool.util.CommonUtil;
import com.github.shoothzj.javatool.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.ConsumerBuilder;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClient;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author hezhangjian
 */
@Slf4j
public class PulsarConsumerSubscribeAsync {

    public static void main(String[] args) throws Exception {
        LogUtil.configureLog();
        // in case this program exit
        new Thread(() -> {
            CommonUtil.sleep(TimeUnit.MINUTES, 10);
        }).start();
        String topic = "test" + UUID.randomUUID().toString();
        log.info("topic is {}", topic);
        PulsarClient pulsarClient = PulsarClient.builder()
                .operationTimeout(1, TimeUnit.SECONDS)
                .serviceUrl("http://127.0.0.1:8080")
                .build();
        final ConsumerBuilder<byte[]> consumerBuilder = pulsarClient.newConsumer();
        try {
            final CompletableFuture<Consumer<byte[]>> completableFuture = consumerBuilder.topic(topic).subscriptionName(UUID.randomUUID().toString()).messageListener(new MessageListener<byte[]>() {
                @Override
                public void received(Consumer<byte[]> consumer, Message<byte[]> message) {
                    log.info("message is [{}]", message);
                }
            }).subscribeAsync();
            completableFuture.exceptionally(new Function<Throwable, Consumer<byte[]>>() {
                @Override
                public Consumer<byte[]> apply(Throwable throwable) {
                    log.error("exception is ", throwable);
                    return null;
                }
            });
        } catch (Throwable e) {
            log.error("exception ", e);
        }
        new Thread(() -> {
            while (true) {
                CommonUtil.sleep(TimeUnit.SECONDS, 5);
            }
        }).start();
    }

}
