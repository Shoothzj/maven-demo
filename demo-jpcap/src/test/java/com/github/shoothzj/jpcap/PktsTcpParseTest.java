package com.github.shoothzj.jpcap;

import com.github.shoothzj.javatool.util.LogUtil;
import com.github.shoothzj.jpcap.print.PrintEnum;
import com.github.shoothzj.jpcap.print.TcpPrintEnum;
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
        final PktsTcpParse pktsTcpParse = new PktsTcpParse();
        pktsTcpParse.printEnum(PrintEnum.ARRIVAL_TIME);
        pktsTcpParse.tcpPrintEnum(TcpPrintEnum.TCP_RAW_CONTENT);
        pktsTcpParse.parse(pcap);
    }

}