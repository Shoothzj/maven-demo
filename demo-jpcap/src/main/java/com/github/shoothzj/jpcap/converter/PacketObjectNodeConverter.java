package com.github.shoothzj.jpcap.converter;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.shoothzj.jpcap.print.PcapEnum;
import com.github.shoothzj.jpcap.util.SortedJacksonUtil;
import io.pkts.packet.Packet;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * @author hezhangjian
 */
@Slf4j
public class PacketObjectNodeConverter {

    private Set<PcapEnum> pcapEnums = new HashSet<>();

    public PacketObjectNodeConverter() {
    }

    public void addPrintEnum(PcapEnum pcapEnum) {
        this.pcapEnums.add(pcapEnum);
    }

    public ObjectNode convert(Packet packet) {
        final ObjectNode objectNode = SortedJacksonUtil.createObjectNode();
        if (pcapEnums.contains(PcapEnum.ARRIVAL_TIME)) {
            objectNode.put(PcapEnum.ARRIVAL_TIME.name(), packet.getArrivalTime());
        }
        return objectNode;
    }

}
