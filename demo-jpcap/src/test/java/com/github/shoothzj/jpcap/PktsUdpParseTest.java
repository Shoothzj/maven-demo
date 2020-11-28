package com.github.shoothzj.jpcap;

import com.github.shoothzj.javatool.util.LogUtil;
import com.github.shoothzj.jpcap.print.PrintEnum;
import com.github.shoothzj.jpcap.print.UdpPrintEnum;
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
        final PktsUdpParse pktsUdpParse = new PktsUdpParse();
        pktsUdpParse.printEnum(PrintEnum.ARRIVAL_TIME);
        pktsUdpParse.udpPrintEnum(UdpPrintEnum.UDP_RAW_CONTENT);
        pktsUdpParse.parse(pcap);
    }
}
