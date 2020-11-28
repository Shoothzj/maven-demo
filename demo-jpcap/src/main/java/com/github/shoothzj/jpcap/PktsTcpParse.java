package com.github.shoothzj.jpcap;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.shoothzj.jpcap.print.TcpPrintEnum;
import com.github.shoothzj.jpcap.util.SortedJacksonUtil;
import io.pkts.buffer.Buffer;
import io.pkts.packet.Packet;
import io.pkts.packet.TCPPacket;
import io.pkts.protocol.Protocol;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * @author hezhangjian
 */
@Slf4j
public class PktsTcpParse extends PktsPacketParse {

    private Set<TcpPrintEnum> tcpPrintEnums;

    public PktsTcpParse() {
        super();
        tcpPrintEnums = new HashSet<>();
    }

    public void tcpPrintEnum(TcpPrintEnum tcpPrintEnum) {
        tcpPrintEnums.add(tcpPrintEnum);
    }

    @Override
    protected void supplyParse(Packet packet) throws Exception {
        if (packet.hasProtocol(Protocol.TCP)) {
            parseTcpPacket((TCPPacket) packet.getPacket(Protocol.TCP));
        }
    }

    private void parseTcpPacket(TCPPacket tcpPacket) {
        final ObjectNode objectNode = SortedJacksonUtil.createObjectNode();
        if (tcpPrintEnums.contains(TcpPrintEnum.TCP_RAW_CONTENT)) {
            final Buffer payload = tcpPacket.getPayload();
            if (payload != null) {
                objectNode.put(TcpPrintEnum.TCP_RAW_CONTENT.name(), new String(payload.getArray()));
            }
        }
        log.info("tcp packet : [{}]", objectNode);
    }

}
