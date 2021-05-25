package com.github.shoothzj.jpcap.handler;

import com.github.shoothzj.jpcap.converter.TcpObjectNodeConverter;
import com.github.shoothzj.jpcap.print.PcapEnum;
import com.github.shoothzj.jpcap.print.TcpEnum;
import io.pkts.packet.TCPPacket;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * @author hezhangjian
 */
@Slf4j
public class TcpPacketPrinter extends PacketPrinter implements TcpPacketHandler {

    private Set<TcpEnum> tcpEnums = new HashSet<>();

    private TcpObjectNodeConverter tcpObjectNodeConverter;

    public TcpPacketPrinter() {
        this.tcpObjectNodeConverter = new TcpObjectNodeConverter();
    }

    @Override
    public void addPrintEnum(PcapEnum pcapEnum) {
        tcpObjectNodeConverter.addPrintEnum(pcapEnum);
    }

    public void tcpPrintEnum(TcpEnum tcpEnum) {
        tcpEnums.add(tcpEnum);
        tcpObjectNodeConverter.addTcpPrintEnum(tcpEnum);
    }

    @Override
    public void onPacket(TCPPacket tcpPacket) {
        log.info("tcp packet : [{}]", tcpObjectNodeConverter.convert(tcpPacket));
    }

}
