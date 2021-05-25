package com.github.shoothzj.jpcap.converter;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.shoothzj.jpcap.print.TcpEnum;
import io.pkts.buffer.Buffer;
import io.pkts.packet.TCPPacket;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * @author hezhangjian
 */
@Slf4j
public class TcpObjectNodeConverter extends PacketObjectNodeConverter {

    private Set<TcpEnum> tcpEnums = new HashSet<>();

    public TcpObjectNodeConverter() {

    }

    public void addTcpPrintEnum(TcpEnum tcpEnum) {
        this.tcpEnums.add(tcpEnum);
    }

    public ObjectNode convert(TCPPacket tcpPacket) {
        final ObjectNode objectNode = super.convert(tcpPacket.getParentPacket());
        if (tcpEnums.contains(TcpEnum.TCP_RAW_CONTENT)) {
            final Buffer payload = tcpPacket.getPayload();
            if (payload != null) {
                objectNode.put(TcpEnum.TCP_RAW_CONTENT.name(), new String(payload.getArray()));
            }
        }
        return objectNode;
    }

}
