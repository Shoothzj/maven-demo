package com.github.shoothzj.jpcap.filter;

import io.pkts.packet.Packet;
import io.pkts.protocol.Protocol;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class PacketTcpFilter implements PacketFilter {
    @Override
    public boolean filter(Packet packet) throws Exception {
        return packet.hasProtocol(Protocol.TCP);
    }
}
