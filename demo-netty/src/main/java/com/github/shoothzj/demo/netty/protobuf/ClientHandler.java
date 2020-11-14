package com.github.shoothzj.demo.netty.protobuf;

import com.github.shoothzj.demo.netty.protobuf.module.ProtoBufModule;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<ProtoBufModule.Response> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ProtoBufModule.Response response) throws Exception {
        log.info("response is [{}]", response);
    }

    /**
     * 当channel激活的时候，客户端立刻发出请求
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final ProtoBufModule.Request request = ProtoBufModule.Request.newBuilder().setFirstName("Akka").setLastName("Scala").build();
        ctx.channel().writeAndFlush(request);
    }
}