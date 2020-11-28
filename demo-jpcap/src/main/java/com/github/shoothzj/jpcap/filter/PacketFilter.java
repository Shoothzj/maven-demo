package com.github.shoothzj.jpcap.filter;

import io.pkts.packet.Packet;

/**
 * @author hezhangjian
 */
public interface PacketFilter {

    boolean filter(Packet packet) throws Exception;

}
