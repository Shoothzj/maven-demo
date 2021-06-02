package com.github.shoothzj.demo.amqp.qpid;

import lombok.extern.slf4j.Slf4j;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;

/**
 * @author hezhangjian
 */
@Slf4j
public class QpidConsumerActiveCli {

    public static void main(String[] args) throws Exception {
        MessageConsumer consumer = QpidUtil.createMessageConsumer();
        receiveMessage(consumer);
    }

    private static void receiveMessage(MessageConsumer consumer) throws JMSException {
        while (true) {
            try {
                // 建议异步处理收到的消息，确保receiveMessage函数里没有耗时逻辑。
                Message message = consumer.receive();
                log.info("receive message {}", QpidUtil.amqpMsg2Str(message));
            } catch (Exception e) {
                log.error("receiveMessage hand an exception: ", e);
            }
        }
    }
}
