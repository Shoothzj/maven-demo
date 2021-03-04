package com.github.shoothzj.demo.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;


/**
 * @author akka
 */
@Slf4j
public class UdpServer {

    private final Bootstrap bootstrap;
    private final NioEventLoopGroup acceptGroup;
    private Channel channel;

    public UdpServer() {
        bootstrap = new Bootstrap();
        acceptGroup = new NioEventLoopGroup();
        bootstrap.group(acceptGroup)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    protected void initChannel(NioDatagramChannel ch)
                            throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new UdpServerHandler());
                    }
                });
    }

    public void start(String host, int port) throws Exception {
        try {
            channel = bootstrap.bind(host, port).sync().channel();
            log.info("UdpServer start success host {} port {}", host, port);
            channel.closeFuture().await();
        } finally {
            acceptGroup.shutdownGracefully();
        }
    }

    public Channel getChannel() {
        return channel;
    }

}