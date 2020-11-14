package com.github.shoothzj.demo.netty.protobuf;

import com.github.shoothzj.demo.netty.protobuf.module.ProtoBufModule;
import com.github.shoothzj.javatool.util.LogUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class ProtoServer {

    private static final int MAX_FRAME_SIZE = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        LogUtil.configureLog();
        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100);
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    final ChannelPipeline pipeline = socketChannel.pipeline();
                    //解码器，通过Google Protocol Buffers序列化框架动态的切割接收到的ByteBuf
                    pipeline.addLast(new ProtobufVarint32FrameDecoder());
                    //服务器端接收的是客户端RequestUser对象，所以这边将接收对象进行解码生产实列
                    pipeline.addLast(new ProtobufDecoder(ProtoBufModule.Request.getDefaultInstance()));
                    //Google Protocol Buffers编码器
                    pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                    //Google Protocol Buffers编码器
                    pipeline.addLast(new ProtobufEncoder());
                    pipeline.addLast(new ServerHandler());
                }
            });

            // Start the server.
            ChannelFuture f = bootstrap.bind(9997).sync();

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
