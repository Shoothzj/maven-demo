package com.github.shoothzj.jpcap;

import com.github.shoothzj.javatool.util.LogUtil;
import com.github.shoothzj.jpcap.handler.PacketPrinter;
import com.github.shoothzj.jpcap.handler.UdpPacketPrinter;
import com.github.shoothzj.jpcap.print.PcapEnum;
import com.github.shoothzj.jpcap.print.UdpEnum;
import com.github.shoothzj.jpcap.util.PcapUtil;
import io.pkts.Pcap;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author hezhangjian
 */
@Slf4j
public class PktsUdpParseTest {

    @Test
    public void testSimpleTcp() throws Exception {
        LogUtil.configureLog();
        final String pcapPath = PcapUtil.getPcapPath("simple_udp", "simple_udp.pcap");
        final Pcap pcap = Pcap.openStream(pcapPath);

        final PacketPrinter packetPrinter = new PacketPrinter();
        packetPrinter.addPrintEnum(PcapEnum.ARRIVAL_TIME);

        final UdpPacketPrinter udpPacketPrinter = new UdpPacketPrinter();
        udpPacketPrinter.udpPrintEnum(UdpEnum.UDP_RAW_CONTENT);
        final PktsUdpParse pktsUdpParse = new PktsUdpParse(udpPacketPrinter);
        pktsUdpParse.addPacketHandler(packetPrinter);
        pktsUdpParse.parse(pcap);
    }
}
