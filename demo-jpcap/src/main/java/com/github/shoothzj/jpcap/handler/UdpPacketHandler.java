package com.github.shoothzj.jpcap.handler;

import io.pkts.packet.UDPPacket;

/**
 * @author hezhangjian
 */
public interface UdpPacketHandler {

    /**
     *
     * @param udpPacket
     */
    void onPacket(UDPPacket udpPacket);

}
