package com.github.shoothzj.jpcap;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.shoothzj.jpcap.filter.PacketFilter;
import com.github.shoothzj.jpcap.handler.ContinuousPacketHandler;
import com.github.shoothzj.jpcap.print.PrintEnum;
import com.github.shoothzj.jpcap.util.SortedJacksonUtil;
import io.pkts.Pcap;
import io.pkts.packet.Packet;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author hezhangjian
 */
@Slf4j
public class PktsPacketParse {

    private List<PacketFilter> packetFilters;

    private Set<PrintEnum> printEnums;

    public PktsPacketParse() {
        packetFilters = new ArrayList<>();
        printEnums = new HashSet<>();
    }

    public PktsPacketParse addFilter(PacketFilter packetFilter) {
        packetFilters.add(packetFilter);
        return this;
    }

    public void printEnum(PrintEnum printEnum) {
        printEnums.add(printEnum);
    }

    public void parse(Pcap pcap) throws Exception {
        pcap.loop(new ContinuousPacketHandler() {
            @Override
            public void processPacket(Packet packet) throws Exception {
                for (PacketFilter packetFilter : packetFilters) {
                    if (!packetFilter.filter(packet)) {
                        return;
                    }
                }
                parsePacket(packet);
                supplyParse(packet);
            }
        });
    }

    protected void supplyParse(Packet packet) throws Exception {

    }

    private void parsePacket(Packet packet) throws Exception {
        final ObjectNode objectNode = SortedJacksonUtil.createObjectNode();
        if (printEnums.contains(PrintEnum.ARRIVAL_TIME)) {
            objectNode.put(PrintEnum.ARRIVAL_TIME.name(), packet.getArrivalTime());
        }
        log.info("packet : [{}]", objectNode);
    }


}
