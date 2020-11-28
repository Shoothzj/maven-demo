package com.github.shoothzj.jpcap.handler;

import io.pkts.PacketHandler;
import io.pkts.packet.Packet;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author hezhangjian
 */
@Slf4j
public abstract class ContinuousPacketHandler implements PacketHandler {

    @Override
    public final boolean nextPacket(Packet packet) throws IOException {
        try {
            processPacket(packet);
        } catch (Exception e) {
            log.error("ignore exception ", e);
        }
        return true;
    }

    public abstract void processPacket(Packet packet) throws Exception;

}
