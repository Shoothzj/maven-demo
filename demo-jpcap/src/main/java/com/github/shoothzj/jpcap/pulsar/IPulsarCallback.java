package com.github.shoothzj.jpcap.pulsar;

import org.apache.pulsar.common.api.proto.PulsarApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author hezhangjian
 */
public interface IPulsarCallback {

    Logger log = LoggerFactory.getLogger(IPulsarCallback.class);

    default void ack() {
        log.info("ack");
    }

    default void connect() {
        log.info("connect");
    }

    default void connected() {
        log.info("connected");
    }

    default void onPartitionedMetadataReq(String topic) {
        log.info("meta data req topic is [{}]", topic);
    }

    default void onPartitionedMetadataResp() {
        log.info("");
    }

    default void onLookupReq(String topic) {
        log.info("lookup topic is [{}]", topic);
    }

    default void onLookupResp(String brokerUrl) {
        log.info("broker url is [{}]", brokerUrl);
    }

    default void onProducerReq(String topic, String producerName, long requestId) {
        log.info("producer topic is [{}] producer name is [{}] request id is [{}]", topic, producerName, requestId);
    }

    default void onProducerResp(String producerName, long requestId) {
        log.info("producer name is [{}] request id is [{}]", producerName, requestId);
    }

    default void onSend(String partitionKey, List<PulsarApi.KeyValue> propertiesList, String msg) {
        log.info("key is [{}] list is [{}] msg is [{}]", partitionKey, propertiesList, msg);
    }

    default void onSendReceipt() {
        log.info("send receipt");
    }

    default void onCloseProducer() {
        log.info("close producer");
    }

    default void onSuccess() {
        log.info("success");
    }

    /**
     * Ping心跳
     */
    default void onPing() {
        log.debug("ping");
    }

    /**
     * Pong回复心跳
     */
    default void onPong() {
        log.debug("pong");
    }

    default void onSubscribe() {
        log.info("subscribe ");
    }

    default void onFlow() {
        log.info("flow ");
    }

    default void onMessage(String partitionKey, List<PulsarApi.KeyValue> propertiesList, String msg) {
        log.info("message key is [{}], propertiesList is [{}] msg is [{}]", partitionKey, propertiesList, msg);
    }

    default void onCloseConsumer() {
        log.info("close consumer");
    }

    default void onActiveConsumerChange() {
        log.info("active consumer change");
    }

}
