package com.github.shoothzj.demo.amqp.qpid;

import lombok.extern.slf4j.Slf4j;

import javax.jms.MessageConsumer;

/**
 * @author hezhangjian
 */
@Slf4j
public class QpidConsumerPassiveCli {

    public static void main(String[] args) throws Exception {
        MessageConsumer consumer = QpidUtil.createMessageConsumer();
        consumer.setMessageListener(message -> log.info("message is {}", QpidUtil.amqpMsg2Str(message)));
    }

}
