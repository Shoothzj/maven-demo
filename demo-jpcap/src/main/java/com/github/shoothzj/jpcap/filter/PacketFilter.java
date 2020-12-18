package com.github.shoothzj.jpcap.filter;

import io.pkts.packet.Packet;

/**
 * @author hezhangjian
 */
public interface PacketFilter {

    /**
     * 过滤packet数据包
     * @param packet
     * @return
     * @throws Exception
     */
    boolean filter(Packet packet) throws Exception;

}
