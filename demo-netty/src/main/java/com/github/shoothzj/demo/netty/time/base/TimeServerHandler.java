package com.github.shoothzj.demo.netty.time.base;

import com.github.shoothzj.demo.netty.time.util.RespUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hezhangjian
 */
@Slf4j
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    private AtomicInteger counter = new AtomicInteger();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        final byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, StandardCharsets.UTF_8);
        counter.incrementAndGet();
        log.info("the time server receive order [{}] count is [{}]", body, counter.get());
        final String result = RespUtil.calResp(body);
        final ByteBuf resp = Unpooled.copiedBuffer(result.getBytes(StandardCharsets.UTF_8));
        ctx.write(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
