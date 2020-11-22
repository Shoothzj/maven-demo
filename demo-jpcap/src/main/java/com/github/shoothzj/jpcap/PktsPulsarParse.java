package com.github.shoothzj.jpcap;

import com.github.shoothzj.javatool.util.LogUtil;
import io.pkts.Pcap;
import io.pkts.buffer.Buffer;
import io.pkts.packet.Packet;
import io.pkts.packet.TCPPacket;
import io.pkts.protocol.Protocol;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.common.api.proto.PulsarApi;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author hezhangjian
 */
@Slf4j
public class PktsPulsarParse {

    public static void main(String[] args) throws IOException {
        LogUtil.configureLog();
        final String pcapPath = PcapUtil.getPcapPath("pulsar-producer-simple.pcap");
        final Pcap pcap = Pcap.openStream(pcapPath);
        pcap.loop(packet -> {
            try {
                log.debug("packet is [{}]", packet);
                if (!packet.hasProtocol(Protocol.TCP)) {
                    return true;
                }
                TCPPacket tcpPacket = (TCPPacket) packet.getPacket(Protocol.TCP);
                final Buffer buffer = tcpPacket.getPayload();
                if (buffer == null) {
                    log.debug("buffer is empty");
                    return true;
                }
                byte[] bufferArray = buffer.getArray();
                byte[] bytes = Arrays.copyOfRange(bufferArray, 8, bufferArray.length);
                // the first four bytes is length of protobuf
                if (tcpPacket.getDestinationPort() == 6650) {
                    // This is client request to pulsar
                    PulsarApi.BaseCommand baseCommand = PulsarApi.BaseCommand.parseFrom(bytes);
                    log.info("buffer is [{}]", baseCommand);
                } else if (tcpPacket.getSourcePort() == 6650) {
                    PulsarApi.BaseCommand baseCommand = PulsarApi.BaseCommand.parseFrom(bytes);
                    log.info("buffer is [{}]", baseCommand);
                }
            } catch (Exception e) {
                log.error("parse error, exception is ", e);
            }
            return true;
        });
    }

}
