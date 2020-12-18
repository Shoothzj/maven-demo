package com.github.shoothzj.demo.iot.mqtt.broker;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.mqtt.MqttConnAckMessage;
import io.netty.handler.codec.mqtt.MqttConnectMessage;
import io.netty.handler.codec.mqtt.MqttConnectPayload;
import io.netty.handler.codec.mqtt.MqttConnectVariableHeader;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessageBuilders;
import lombok.extern.slf4j.Slf4j;

import static io.netty.handler.codec.mqtt.MqttConnectReturnCode.CONNECTION_ACCEPTED;

/**
 * 处理Mqtt协议栈
 *
 * @author hezhangjian
 */
@Slf4j
public class MqttHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        if (msg instanceof MqttConnectMessage) {
            handleConnect(ctx, (MqttConnectMessage) msg);
        } else {
            log.error("Unsupported type msg [{}]", msg);
        }
    }

    private void handleConnect(ChannelHandlerContext ctx, MqttConnectMessage connectMessage) {
        log.info("connect msg is [{}]", connectMessage);
        final MqttFixedHeader fixedHeader = connectMessage.fixedHeader();
        final MqttConnectVariableHeader variableHeader = connectMessage.variableHeader();
        final MqttConnectPayload connectPayload = connectMessage.payload();
        final MqttConnAckMessage ackMessage = MqttMessageBuilders.connAck().returnCode(CONNECTION_ACCEPTED).build();
        ctx.channel().writeAndFlush(ackMessage);
    }

}
