package com.github.shoothzj.jpcap;

import com.github.shoothzj.javatool.util.LogUtil;
import io.pkts.Pcap;
import io.pkts.buffer.Buffer;
import io.pkts.protocol.Protocol;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author hezhangjian
 */
@Slf4j
public class PktsTest {

    public static void main(String[] args) throws IOException {
        LogUtil.configureLog();
        final String pcapPath = PcapUtil.getPcapPath(PcapUtil.TCP_TIMESTAMP, "inside_docker-modify.pcap");
        final Pcap pcap = Pcap.openStream(pcapPath);
        pcap.loop(packet -> {
            log.info("[{}]", packet);
            if (packet.hasProtocol(Protocol.TCP)) {
                final Buffer buffer = packet.getPacket(Protocol.TCP).getPayload();
                log.info("buffer is [{}]", buffer);
            }
            return true;
        });
    }

}
