package com.github.shoothzj.demo.amqp.rabbitmq;

import com.github.shoothzj.demo.amqp.AmqpConst;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class RabbitmqUtil {

    public static ConnectionFactory connectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(AmqpConst.HOST);
        factory.setUsername(AmqpConst.USERNAME);
        factory.setPassword(AmqpConst.PASSWORD);
        return factory;
    }

}
