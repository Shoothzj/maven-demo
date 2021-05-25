package com.github.shoothzj.jpcap;

import com.github.shoothzj.jpcap.filter.PacketFilter;
import com.github.shoothzj.jpcap.handler.ContinuousPacketHandler;
import com.github.shoothzj.jpcap.handler.PacketHandler;
import io.pkts.Pcap;
import io.pkts.packet.Packet;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hezhangjian
 */
@Slf4j
public class PktsPacketParse {

    private List<PacketFilter> packetFilters;

    private List<PacketHandler> packetHandlerList;

    public PktsPacketParse() {
        this.packetFilters = new ArrayList<>();
        this.packetHandlerList = new ArrayList<>();
    }

    public PktsPacketParse addFilter(PacketFilter packetFilter) {
        this.packetFilters.add(packetFilter);
        return this;
    }

    public PktsPacketParse addPacketHandler(PacketHandler packetHandler) {
        this.packetHandlerList.add(packetHandler);
        return this;
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
                handlePacket(packet);
                afterPacketFilter(packet);
            }
        });
    }

    protected void afterPacketFilter(Packet packet) throws Exception {

    }

    private void handlePacket(Packet packet) throws Exception {
        for (PacketHandler packetHandler : packetHandlerList) {
            packetHandler.onPacket(packet);
        }
    }


}
