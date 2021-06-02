package com.github.shoothzj.demo.amqp.qpid;

import com.github.shoothzj.demo.amqp.AmqpConst;
import com.github.shoothzj.javatool.util.CommonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.JmsConnection;
import org.apache.qpid.jms.JmsConnectionFactory;

import javax.jms.CompletionListener;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import java.util.concurrent.TimeUnit;

/**
 * @author hezhangjian
 */
@Slf4j
public class QpidProducerCli {

    public static void main(String[] args) throws Exception {
        final InitialContext context = new InitialContext(QpidUtil.getParamTable());
        final JmsConnectionFactory cf = (JmsConnectionFactory) context.lookup("ConnectionURL");
        final Destination queue = (Destination) context.lookup("QueueName");

        final Connection connection = cf.createConnection(AmqpConst.USERNAME, AmqpConst.PASSWORD);
        ((JmsConnection) connection).addConnectionListener(new CustomConnectionListener());
        // 创建 Session
        // Session.CLIENT_ACKNOWLEDGE: 收到消息后，需要手动调用message.acknowledge()。
        // Session.AUTO_ACKNOWLEDGE: SDK自动ACK（推荐）。
        Session session = connection.createSession();
        connection.start();
        final MessageProducer messageProducer = session.createProducer(queue);
        TextMessage message = session.createTextMessage("Text!");
        messageProducer.send(message, DeliveryMode.PERSISTENT, Message.DEFAULT_PRIORITY, Message.DEFAULT_TIME_TO_LIVE, new CompletionListener() {
            @SneakyThrows
            @Override
            public void onCompletion(Message message) {
                log.info("complete message is {}", message.getBody(String.class));
            }

            @Override
            public void onException(Message message, Exception e) {
                log.error("exception is ", e);
            }
        });
        CommonUtil.sleep(TimeUnit.SECONDS, 3);
    }


}
