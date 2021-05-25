package com.github.shoothzj.jpcap.handler;

import io.pkts.packet.Packet;

/**
 * @author hezhangjian
 */
public interface PacketHandler {

    /**
     *
     * @param packet
     */
    void onPacket(Packet packet);

}
