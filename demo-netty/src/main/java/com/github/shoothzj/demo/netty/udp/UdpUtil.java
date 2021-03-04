package com.github.shoothzj.demo.netty.udp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * @author hezhangjian
 */
@Slf4j
public class UdpUtil {

    /**
     * 组装数据包
     *
     * @param msg               消息串
     * @param inetSocketAddress 服务器地址
     * @return DatagramPacket
     */
    public static DatagramPacket datagramPacket(String msg, InetSocketAddress inetSocketAddress) {
        ByteBuf dataBuf = Unpooled.copiedBuffer(msg, StandardCharsets.UTF_8);
        return new DatagramPacket(dataBuf, inetSocketAddress);
    }

}
