package com.github.shoothzj.jpcap;

import com.github.shoothzj.javatool.util.LogUtil;
import com.github.shoothzj.jpcap.ex.PulsarDecodeException;
import com.github.shoothzj.jpcap.pulsar.IPulsarCallback;
import com.github.shoothzj.jpcap.util.PcapUtil;
import com.github.shoothzj.jpcap.util.PulsarDecodeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.pkts.Pcap;
import io.pkts.buffer.Buffer;
import io.pkts.packet.TCPPacket;
import io.pkts.protocol.Protocol;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.common.api.proto.PulsarApi;
import org.apache.pulsar.common.util.protobuf.ByteBufCodedInputStream;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author hezhangjian
 */
@Slf4j
public class PktsPulsarParseTest {

    public static void main(String[] args) throws IOException {
        LogUtil.configureLog();
        final String pcapPath = PcapUtil.getPcapPath("pulsar", "pulsar-producer-and-consumer-simple.pcap");
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
                byte[] bytes = Arrays.copyOfRange(bufferArray, 0, bufferArray.length);
                // the first four bytes is length of protobuf
                if (tcpPacket.getDestinationPort() == 6650) {
                    // This is client request to pulsar
                    parse(bytes);
                } else if (tcpPacket.getSourcePort() == 6650) {
                    parse(bytes);
                }
            } catch (PulsarDecodeException pulsarDecodeException) {
                log.error("pulsar decode exception, exception is ", pulsarDecodeException);
                return false;
            } catch (Exception e) {
                log.error("parse error, exception is ", e);
            }
            return true;
        });
    }

    private static void parse(byte[] javaBuffer) throws IOException {
        ByteBuf buffer = Unpooled.copiedBuffer(javaBuffer);
        buffer.readInt();
        int cmdSize = (int) buffer.readUnsignedInt();
        final int writeIndex = buffer.writerIndex();
        buffer.writerIndex(buffer.readerIndex() + cmdSize);
        ByteBufCodedInputStream cmdInputStream = ByteBufCodedInputStream.get(buffer);
        final PulsarApi.BaseCommand.Builder cmdBuilder = PulsarApi.BaseCommand.newBuilder();
        PulsarApi.BaseCommand baseCommand = cmdBuilder.mergeFrom(cmdInputStream, null).build();
        buffer.writerIndex(writeIndex);
        PulsarDecodeUtil.parsePulsarCommand(baseCommand, buffer, new IPulsarCallback() {
        });
    }

}
