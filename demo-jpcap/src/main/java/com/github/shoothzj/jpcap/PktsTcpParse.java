package com.github.shoothzj.jpcap;

import com.github.shoothzj.jpcap.filter.TcpFilter;
import com.github.shoothzj.jpcap.handler.TcpPacketHandler;
import io.pkts.packet.Packet;
import io.pkts.packet.TCPPacket;
import io.pkts.protocol.Protocol;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hezhangjian
 */
@Slf4j
public class PktsTcpParse extends PktsPacketParse {

    private List<TcpFilter> tcpFilters;

    private List<TcpPacketHandler> tcpPacketHandlerList;

    public PktsTcpParse() {
        super();
        this.tcpFilters = new ArrayList<>();
        this.tcpPacketHandlerList = new ArrayList<>();
    }

    public void tcpFilter(TcpFilter tcpFilter) {
        tcpFilters.add(tcpFilter);
    }

    public void tcpPacketHandler(TcpPacketHandler tcpPacketHandler) {
        tcpPacketHandlerList.add(tcpPacketHandler);
    }

    @Override
    protected void afterPacketFilter(Packet packet) throws Exception {
        if (packet.hasProtocol(Protocol.TCP)) {
            final TCPPacket tcpPacket = (TCPPacket) packet.getPacket(Protocol.TCP);
            for (TcpFilter tcpFilter : tcpFilters) {
                if (!tcpFilter.filter(tcpPacket)) {
                    return;
                }
            }
            parseTcpPacket(tcpPacket);
        }
    }

    private void parseTcpPacket(TCPPacket tcpPacket) {
        for (TcpPacketHandler tcpPacketHandler : tcpPacketHandlerList) {
            tcpPacketHandler.onPacket(tcpPacket);
        }
    }

}
