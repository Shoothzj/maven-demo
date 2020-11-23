package com.github.shoothzj.jpcap.util;

import com.github.shoothzj.jpcap.ex.PulsarDecodeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.common.api.proto.PulsarApi;

/**
 * @author hezhangjian
 */
@Slf4j
public class PulsarDecodeUtil {

    public static void parsePulsarCommand(PulsarApi.BaseCommand baseCommand) {
        final PulsarApi.BaseCommand.Type commandType = baseCommand.getType();
        switch (commandType) {
            case ACK:
                final PulsarApi.CommandAck commandAck = baseCommand.getAck();
                log.info("command ack is [{}]", commandAck);
                break;
            case CONNECT:
                final PulsarApi.CommandConnect connect = baseCommand.getConnect();
                log.info("command connect is [{}]", connect);
                break;
            case CONNECTED:
                final PulsarApi.CommandConnected connected = baseCommand.getConnected();
                log.info("command connected is [{}]", connected);
                break;
            case PARTITIONED_METADATA:
                final PulsarApi.CommandPartitionedTopicMetadata partitionMetadata = baseCommand.getPartitionMetadata();
                log.info("partition meta is [{}]", partitionMetadata);
                break;
            default:
                throw new PulsarDecodeException(String.format("not supported type %s", commandType));
        }
    }

}
