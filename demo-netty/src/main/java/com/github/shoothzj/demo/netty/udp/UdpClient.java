package com.github.shoothzj.demo.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;


/**
 * @author akka
 */
@Slf4j
public class UdpClient {

    private final Bootstrap bootstrap;
    public final NioEventLoopGroup workerGroup;
    public Channel channel;

    public UdpClient() {
        bootstrap = new Bootstrap();
        workerGroup = new NioEventLoopGroup();
        bootstrap.group(workerGroup)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    protected void initChannel(NioDatagramChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new UdpClientHandler());
                    }
                });
    }

    public void start() throws Exception {
        channel = bootstrap.bind(0).sync().channel();
        channel.closeFuture().await(1000);
    }

    public Channel getChannel() {
        return channel;
    }

    public void sendMessage(String msg, InetSocketAddress inetSocketAddress) {
        final DatagramPacket datagramPacket = UdpUtil.datagramPacket(msg, inetSocketAddress);
        if (channel != null) {
            channel.writeAndFlush(datagramPacket).addListener((GenericFutureListener<ChannelFuture>) future -> {
                boolean success = future.isSuccess();
                log.info("Sender datagramPacket result {}", success);
            });
        } else {
            throw new NullPointerException("channel is null");
        }
    }
}