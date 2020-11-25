package com.github.shoothzj.jpcap.util;

import com.github.shoothzj.jpcap.ex.PulsarDecodeException;
import com.github.shoothzj.jpcap.pulsar.IPulsarCallback;
import io.netty.buffer.ByteBuf;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.common.api.proto.PulsarApi;
import org.apache.pulsar.common.protocol.Commands;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author hezhangjian
 */
@Slf4j
public class PulsarDecodeUtil {

    @SneakyThrows
    public static void parsePulsarCommand(PulsarApi.BaseCommand baseCommand, ByteBuf buffer, IPulsarCallback pulsarCallback) {
        final PulsarApi.BaseCommand.Type commandType = baseCommand.getType();
        switch (commandType) {
            case ACK:
                pulsarCallback.ack();
                break;
            case CONNECT:
                pulsarCallback.connect();
                break;
            case CONNECTED:
                pulsarCallback.connected();
                break;
            case PARTITIONED_METADATA:
                final PulsarApi.CommandPartitionedTopicMetadata partitionMetadata = baseCommand.getPartitionMetadata();
                pulsarCallback.onPartitionedMetadataReq(partitionMetadata.getTopic());
                break;
            case PARTITIONED_METADATA_RESPONSE:
                pulsarCallback.onPartitionedMetadataResp();
                break;
            case LOOKUP:
                final PulsarApi.CommandLookupTopic lookupTopic = baseCommand.getLookupTopic();
                pulsarCallback.onLookupReq(lookupTopic.getTopic());
                break;
            case LOOKUP_RESPONSE:
                final PulsarApi.CommandLookupTopicResponse lookupTopicResponse = baseCommand.getLookupTopicResponse();
                pulsarCallback.onLookupResp(lookupTopicResponse.getBrokerServiceUrl());
                break;
            case PRODUCER:
                final PulsarApi.CommandProducer producer = baseCommand.getProducer();
                pulsarCallback.onProducerReq(producer.getTopic(), producer.getProducerName(), producer.getRequestId());
                break;
            case PRODUCER_SUCCESS:
                final PulsarApi.CommandProducerSuccess producerSuccess = baseCommand.getProducerSuccess();
                pulsarCallback.onProducerResp(producerSuccess.getProducerName(), producerSuccess.getRequestId());
                break;
            case SEND:
                parseSend(baseCommand, buffer, pulsarCallback);
                break;
            case SEND_RECEIPT:
                final PulsarApi.CommandSendReceipt sendReceipt = baseCommand.getSendReceipt();
                pulsarCallback.onSendReceipt();
                break;
            case CLOSE_PRODUCER:
                final PulsarApi.CommandCloseProducer closeProducer = baseCommand.getCloseProducer();
                pulsarCallback.onCloseProducer();
                break;
            case SUCCESS:
                final PulsarApi.CommandSuccess success = baseCommand.getSuccess();
                pulsarCallback.onSuccess();
                break;
            case PING:
                PulsarApi.CommandPing ping = baseCommand.getPing();
                pulsarCallback.onPing();
                break;
            case PONG:
                PulsarApi.CommandPong pong = baseCommand.getPong();
                pulsarCallback.onPong();
                break;
            case SUBSCRIBE:
                PulsarApi.CommandSubscribe subscribe = baseCommand.getSubscribe();
                pulsarCallback.onSubscribe();
                break;
            case FLOW:
                PulsarApi.CommandFlow flow = baseCommand.getFlow();
                pulsarCallback.onFlow();
                break;
            case MESSAGE:
                PulsarApi.CommandMessage message = baseCommand.getMessage();
                pulsarCallback.onMessage();
                break;
            case CLOSE_CONSUMER:
                PulsarApi.CommandCloseConsumer closeConsumer = baseCommand.getCloseConsumer();
                pulsarCallback.onCloseConsumer();
                break;
            default:
                throw new PulsarDecodeException(String.format("not supported type %s", commandType));
        }
    }

    private static void parseSend(PulsarApi.BaseCommand baseCommand, ByteBuf buffer, IPulsarCallback pulsarCallback) throws IOException {
        // This is send req, the body in rest
        final PulsarApi.CommandSend commandSend = baseCommand.getSend();
        // store a buffer marking the content + headers
        ByteBuf headersAndPayload = buffer.markReaderIndex();
        final PulsarApi.MessageMetadata metadata = Commands.parseMessageMetadata(headersAndPayload);
        final ByteBuf message = buffer.retain();
        byte[] bytes = new byte[message.readableBytes()];
        message.readBytes(bytes);

        // read first four bytes
        int len1 = ((bytes[0] * 16 + bytes[1]) * 16 + bytes[2])*16 + bytes[3];
        final byte[] messageBytes = Arrays.copyOfRange(bytes, len1 + 4, bytes.length);
        pulsarCallback.onSend(metadata.getPartitionKey(), metadata.getPropertiesList(),new String(messageBytes));
    }

}
