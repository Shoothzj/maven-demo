package com.github.shoothzj.demo.netty.udp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;


/**
 * @author akka
 */
@Slf4j
public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        // 接受client的消息
        log.info("begin read from client");
        final ByteBuf buf = msg.content();
        int readableBytes = buf.readableBytes();
        byte[] content = new byte[readableBytes];
        buf.readBytes(content);
        String clientMessage = new String(content, StandardCharsets.UTF_8);
        log.info("clientMessage is: " + clientMessage);
        if (clientMessage.equals(UdpConstant.CLIENT_REQ)) {
            ctx.writeAndFlush(new DatagramPacket(Unpooled.wrappedBuffer(UdpConstant.SERVER_RESP.getBytes()), msg.sender()));
        }
    }

}