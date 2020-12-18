package com.github.shoothzj.jpcap.filter;

import io.pkts.buffer.Buffer;
import io.pkts.packet.TCPPacket;

/**
 * @author hezhangjian
 */
public class TcpRawContentFilter implements TcpFilter {

    private String msg;

    public TcpRawContentFilter(String msg) {
        this.msg = msg;
    }

    @Override
    public boolean filter(TCPPacket packet) throws Exception {
        final Buffer payload = packet.getPayload();
        if (payload == null) {
            return false;
        }
        final byte[] array = payload.getArray();
        return new String(array).contains(msg);
    }

}
