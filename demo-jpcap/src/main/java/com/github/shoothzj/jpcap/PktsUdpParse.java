package com.github.shoothzj.jpcap;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.shoothzj.jpcap.print.UdpPrintEnum;
import com.github.shoothzj.jpcap.util.SortedJacksonUtil;
import io.pkts.buffer.Buffer;
import io.pkts.packet.Packet;
import io.pkts.packet.UDPPacket;
import io.pkts.protocol.Protocol;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * @author hezhangjian
 */
@Slf4j
public class PktsUdpParse extends PktsPacketParse {

    private Set<UdpPrintEnum> udpPrintEnums;

    public PktsUdpParse() {
        udpPrintEnums = new HashSet<>();
    }

    public void udpPrintEnum(UdpPrintEnum udpPrintEnum) {
        udpPrintEnums.add(udpPrintEnum);
    }

    @Override
    protected void supplyParse(Packet packet) throws Exception {
        if (packet.hasProtocol(Protocol.UDP)) {
            parseUdpPacket((UDPPacket) packet.getPacket(Protocol.UDP));
        }
        super.supplyParse(packet);
    }

    private void parseUdpPacket(UDPPacket udpPacket) {
        final ObjectNode objectNode = SortedJacksonUtil.createObjectNode();
        if (udpPrintEnums.contains(UdpPrintEnum.UDP_RAW_CONTENT)) {
            final Buffer payload = udpPacket.getPayload();
            if (payload != null) {
                objectNode.put(UdpPrintEnum.UDP_RAW_CONTENT.name(), new String(payload.getArray()));
            }
        }
        log.info("udp packet : [{}]", objectNode);
    }

}
