package com.github.shoothzj.demo.skywalking.pulsar.producer;

import com.github.shoothzj.demo.skywalking.pulsar.PulsarConst;
import com.github.shoothzj.javatool.util.CommonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.ProducerBuilder;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.api.TypedMessageBuilder;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author hezhangjian
 */
@Slf4j
public class PulsarProducerMain {

    @SneakyThrows
    public static Producer<String> createProducer() {
        PulsarClient pulsarClient = PulsarClient.builder()
                .serviceUrl("http://127.0.0.1:8080")
                .build();
        ProducerBuilder<String> producerBuilder = pulsarClient.newProducer(Schema.STRING).enableBatching(false);
        return producerBuilder.topic(PulsarConst.TOPIC).create();
    }

    public static void sendRecord(Producer<String> producer) {
        final TypedMessageBuilder<String> stringTypedMessageBuilder = producer.newMessage();
        final HashMap<String, String> map = new HashMap<>();
        map.put("1", "2");
        final TypedMessageBuilder<String> value = stringTypedMessageBuilder.key("1").value("2").properties(map);
        final CompletableFuture<MessageId> completableFuture = value.sendAsync();
        completableFuture.exceptionally(new Function<Throwable, MessageId>() {
            @Override
            public MessageId apply(Throwable throwable) {
                log.error("send error ", throwable.getCause());
                return null;
            }
        });
    }

    @SneakyThrows
    public static void main(String[] args) {
        final Producer<String> producer = createProducer();
        while (true) {
            sendRecord(producer);
            CommonUtil.sleep(TimeUnit.SECONDS, 1);
        }
    }

}
