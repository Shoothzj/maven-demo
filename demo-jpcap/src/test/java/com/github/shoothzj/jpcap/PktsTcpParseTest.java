package com.github.shoothzj.jpcap;

import com.github.shoothzj.javatool.util.LogUtil;
import com.github.shoothzj.jpcap.filter.PacketTcpFilter;
import com.github.shoothzj.jpcap.filter.TcpRawContentFilter;
import com.github.shoothzj.jpcap.handler.PacketPrinter;
import com.github.shoothzj.jpcap.handler.TcpPacketPrinter;
import com.github.shoothzj.jpcap.print.PcapEnum;
import com.github.shoothzj.jpcap.print.TcpEnum;
import com.github.shoothzj.jpcap.util.PcapUtil;
import io.pkts.Pcap;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class PktsTcpParseTest {

    @Test
    public void testSimpleTcp() throws Exception {
        LogUtil.configureLog();
        final String pcapPath = PcapUtil.getPcapPath("simple_tcp", "simple_tcp.pcap");
        final Pcap pcap = Pcap.openStream(pcapPath);

        final PacketPrinter packetPrinter = new PacketPrinter();
        packetPrinter.addPrintEnum(PcapEnum.ARRIVAL_TIME);

        final TcpPacketPrinter tcpPacketPrinter = new TcpPacketPrinter();
        tcpPacketPrinter.tcpPrintEnum(TcpEnum.TCP_RAW_CONTENT);

        final PktsTcpParse pktsTcpParse = new PktsTcpParse();
        pktsTcpParse.addPacketHandler(packetPrinter);
        pktsTcpParse.parse(pcap);
    }

    @Test
    public void testSimpleTcpContainsHello() throws Exception {
        LogUtil.configureLog();
        final String pcapPath = PcapUtil.getPcapPath("simple_tcp", "simple_tcp.pcap");
        final Pcap pcap = Pcap.openStream(pcapPath);

        final TcpPacketPrinter tcpPacketPrinter = new TcpPacketPrinter();
        tcpPacketPrinter.addPrintEnum(PcapEnum.ARRIVAL_TIME);
        tcpPacketPrinter.tcpPrintEnum(TcpEnum.TCP_RAW_CONTENT);
        final PktsTcpParse pktsTcpParse = new PktsTcpParse();
        pktsTcpParse.tcpFilter(new TcpRawContentFilter("Hello"));
        pktsTcpParse.tcpPacketHandler(tcpPacketPrinter);
        pktsTcpParse.parse(pcap);
    }

    @Test
    public void testTcpFlag() throws Exception {
        LogUtil.configureLog();
        final String pcapPath = PcapUtil.getPcapPath("simple_tcp", "simple_tcp.pcap");
        final Pcap pcap = Pcap.openStream(pcapPath);

        final PacketPrinter packetPrinter = new PacketPrinter();
        packetPrinter.addPrintEnum(PcapEnum.ARRIVAL_TIME);

        final TcpPacketPrinter tcpPacketPrinter = new TcpPacketPrinter();
        tcpPacketPrinter.tcpPrintEnum(TcpEnum.TCP_RAW_CONTENT);
        final PktsTcpParse pktsTcpParse = new PktsTcpParse();
        pktsTcpParse.addFilter(new PacketTcpFilter());
        pktsTcpParse.addPacketHandler(packetPrinter);
        pktsTcpParse.parse(pcap);
    }

}