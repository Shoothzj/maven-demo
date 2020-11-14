package com.github.shoothzj.demo.netty.protobuf;

import com.github.shoothzj.demo.netty.protobuf.module.ProtoBufModule;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler<ProtoBufModule.Request> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ProtoBufModule.Request request) throws Exception {
        log.info("request [{}]", request);
        final ProtoBufModule.Response response = ProtoBufModule.Response.newBuilder().setStatus(200).build();

        channelHandlerContext.channel().writeAndFlush(response);
    }
}
