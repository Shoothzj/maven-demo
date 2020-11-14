package com.github.shoothzj.demo.netty.protobuf;

import com.github.shoothzj.demo.netty.protobuf.module.ProtoBufModule;
import com.github.shoothzj.javatool.util.LogUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class ProtoClient {

    public static void main(String[] args) throws Exception {
        LogUtil.configureLog();
        // configure the client
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //解码器，通过Google Protocol Buffers序列化框架动态的切割接收到的ByteBuf
                            pipeline.addLast(new ProtobufVarint32FrameDecoder());
                            //将接收到的二进制文件解码成具体的实例，这边接收到的是服务端的ResponseBank对象实列
                            pipeline.addLast(new ProtobufDecoder(ProtoBufModule.Response.getDefaultInstance()));
                            //Google Protocol Buffers编码器
                            pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                            //Google Protocol Buffers编码器
                            pipeline.addLast(new ProtobufEncoder());

                            pipeline.addLast(new ClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9997).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

}
