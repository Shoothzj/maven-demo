package com.github.shoothzj.jpcap.handler;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.shoothzj.jpcap.print.UdpEnum;
import com.github.shoothzj.jpcap.util.SortedJacksonUtil;
import io.pkts.buffer.Buffer;
import io.pkts.packet.UDPPacket;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * @author hezhangjian
 */
@Slf4j
public class UdpPacketPrinter implements UdpPacketHandler {

    private Set<UdpEnum> udpEnums = new HashSet<>();

    public UdpPacketPrinter() {
    }

    public void udpPrintEnum(UdpEnum udpEnum) {
        udpEnums.add(udpEnum);
    }

    @Override
    public void onPacket(UDPPacket udpPacket) {
        final ObjectNode objectNode = SortedJacksonUtil.createObjectNode();
        if (udpEnums.contains(UdpEnum.UDP_RAW_CONTENT)) {
            final Buffer payload = udpPacket.getPayload();
            if (payload != null) {
                objectNode.put(UdpEnum.UDP_RAW_CONTENT.name(), new String(payload.getArray()));
            }
        }
        log.info("udp packet : [{}]", objectNode);
    }

}
