package com.github.shoothzj.demo.netty.time.base;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class TimeClient {

    public static void main(String[] args) throws Exception {
        final TimeClient timeClient = new TimeClient();
        timeClient.connect("127.0.0.1", 8080, 100);
    }

    public void connect(String host, int port, int sendCount) throws Exception {
        final NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            final Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TimeClientHandler(sendCount));
                        }
                    });
            // connect
            final ChannelFuture f = b.connect(host, port).sync();
            // wait for client close
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

}
