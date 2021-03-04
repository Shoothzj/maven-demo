package com.github.shoothzj.demo.netty;

import com.github.shoothzj.javatool.util.LogUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author hezhangjian
 */
@Slf4j
public class NettyChoosePortTest {

    @Test
    public void choosePort() throws InterruptedException {
        LogUtil.configureLog();
        final Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        bootstrap.group(workerGroup)
                .channel(NioDatagramChannel.class)
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    protected void initChannel(NioDatagramChannel ch)throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                    }
                });
        final ChannelFuture channelFuture = bootstrap.bind("127.0.0.1", 0);
        channelFuture.channel().closeFuture().sync();
    }

}
