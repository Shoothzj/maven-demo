package com.github.shoothzj.demo.netty.time.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author hezhangjian
 */
@Slf4j
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private int sendCount;

    private byte[] req;

    public TimeClientHandler() {
        this(1);
    }

    public TimeClientHandler(int sendCount) {
        this.sendCount = sendCount;
        this.req = "QUERY TIME ORDER".getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < sendCount; i++) {
            ByteBuf message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        final byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, StandardCharsets.UTF_8);
        log.info("now is [{}]", body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("exception is ", cause);
        ctx.close();
    }
}
