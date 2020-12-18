package com.github.shoothzj.jpcap.filter;

import io.pkts.packet.TCPPacket;

/**
 * @author hezhangjian
 */
public interface TcpFilter {

    /**
     * 过滤tcp的数据包文
     * @param packet
     * @return
     * @throws Exception
     */
    boolean filter(TCPPacket packet) throws Exception;

}
