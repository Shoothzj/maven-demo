package com.github.shoothzj.demo.pulsar;

import com.github.shoothzj.javatool.util.CommonUtil;
import com.github.shoothzj.javatool.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.BatchReceivePolicy;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.ConsumerBuilder;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.Messages;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author hezhangjian
 */
@Slf4j
public class PulsarConsumer {

    public static void main(String[] args) throws Exception {
        LogUtil.configureLog();
        // in case this program exit
        new Thread(() -> {
            CommonUtil.sleep(TimeUnit.MINUTES, 10);
        }).start();
        String topic = "test";
        log.info("topic is {}", topic);
        PulsarClient pulsarClient = PulsarClient.builder()
                .operationTimeout(1, TimeUnit.SECONDS)
                .serviceUrl("http://127.0.0.1:8080")
                .build();
        final ConsumerBuilder<byte[]> consumerBuilder = pulsarClient.newConsumer();
        Consumer<byte[]> consumer = null;
        try {
            consumer = consumerBuilder.topic(topic).batchReceivePolicy(BatchReceivePolicy.builder().timeout(1, TimeUnit.SECONDS).maxNumMessages(1).build()).subscriptionName(UUID.randomUUID().toString()).subscribe();
        } catch (Throwable e) {
            log.error("exception ", e);
        }
        Consumer<byte[]> finalConsumer1 = consumer;
        new Thread(() -> {
            while (true) {
                    CompletableFuture<Messages<byte[]>> messagesCompletableFuture = finalConsumer1.batchReceiveAsync();
                    messagesCompletableFuture.thenAccept(new java.util.function.Consumer<Messages<byte[]>>() {
                        @Override
                        public void accept(Messages<byte[]> messages) {
                            log.info("fk");
                        }
                    });
            }
        }).start();
        Consumer<byte[]> finalConsumer = consumer;
        new Thread(() -> {
            while (true) {
                CommonUtil.sleep(TimeUnit.SECONDS, 5);
            }
        }).start();
    }

}
