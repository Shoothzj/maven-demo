package com.github.shoothzj.jpcap.handler;

import io.pkts.packet.TCPPacket;

/**
 * @author hezhangjian
 */
public interface TcpPacketHandler extends PacketHandler {

    /**
     *
     * @param tcpPacket
     */
    void onPacket(TCPPacket tcpPacket);

}
