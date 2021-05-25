package com.github.shoothzj.jpcap;

import com.github.shoothzj.jpcap.handler.UdpPacketHandler;
import io.pkts.packet.Packet;
import io.pkts.packet.UDPPacket;
import io.pkts.protocol.Protocol;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class PktsUdpParse extends PktsPacketParse {

    private UdpPacketHandler udpPacketHandler;

    public PktsUdpParse(UdpPacketHandler udpPacketHandler) {
        super();
        this.udpPacketHandler = udpPacketHandler;
    }

    @Override
    protected void afterPacketFilter(Packet packet) throws Exception {
        if (packet.hasProtocol(Protocol.UDP)) {
            parseUdpPacket((UDPPacket) packet.getPacket(Protocol.UDP));
        }
    }

    private void parseUdpPacket(UDPPacket udpPacket) {
        udpPacketHandler.onPacket(udpPacket);
    }

}
