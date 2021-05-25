package com.github.shoothzj.jpcap.handler;

import com.github.shoothzj.jpcap.converter.PacketObjectNodeConverter;
import com.github.shoothzj.jpcap.print.PcapEnum;
import io.pkts.packet.Packet;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * @author hezhangjian
 */
@Slf4j
public class PacketPrinter implements PacketHandler{

    private Set<PcapEnum> pcapEnums = new HashSet<>();

    private PacketObjectNodeConverter packetObjectNodeConverter;

    public PacketPrinter() {
        this.packetObjectNodeConverter = new PacketObjectNodeConverter();
    }

    public void addPrintEnum(PcapEnum pcapEnum) {
        pcapEnums.add(pcapEnum);
        packetObjectNodeConverter.addPrintEnum(pcapEnum);
    }

    @Override
    public void onPacket(Packet packet) {
        log.info("packet : [{}]", packetObjectNodeConverter.convert(packet));
    }

}
