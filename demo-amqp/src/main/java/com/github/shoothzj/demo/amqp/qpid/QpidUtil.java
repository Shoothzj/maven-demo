package com.github.shoothzj.demo.amqp.qpid;

import com.github.shoothzj.demo.amqp.AmqpConst;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.JmsConnection;
import org.apache.qpid.jms.JmsConnectionFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

/**
 * @author hezhangjian
 */
@Slf4j
public class QpidUtil {

    public static String getConnectionUrl() {
        return "amqp://localhost:" + AmqpConst.PORT + "?amqp.vhost=default&amqp.saslMechanisms=PLAIN";
    }

    public static Hashtable<String, String> getParamTable() {
        final Hashtable<String, String> map = new Hashtable<>();
        map.put("connectionfactory.ConnectionURL", getConnectionUrl());
        map.put("queue.QueueName", AmqpConst.QUEUE_NAME);
        map.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.jms.jndi.JmsInitialContextFactory");
        return map;
    }

    @SneakyThrows
    public static InitialContext createInitialContext() {
        return new InitialContext(getParamTable());
    }

    @SneakyThrows
    public static MessageConsumer createMessageConsumer() {
        final InitialContext context = createInitialContext();
        final JmsConnectionFactory cf = (JmsConnectionFactory) context.lookup("ConnectionURL");
        final Destination queue = (Destination) context.lookup("QueueName");

        final Connection connection = cf.createConnection(AmqpConst.USERNAME, AmqpConst.PASSWORD);
        ((JmsConnection) connection).addConnectionListener(new CustomConnectionListener());
        connection.start();
        // 创建 Session
        // Session.CLIENT_ACKNOWLEDGE: 收到消息后，需要手动调用message.acknowledge()。
        // Session.AUTO_ACKNOWLEDGE: SDK自动ACK（推荐）。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建 Receiver Link
        return session.createConsumer(queue);
    }

    @SneakyThrows
    public static String amqpMsg2Str(Message message) {
        return message.getBody(String.class);
    }


}
