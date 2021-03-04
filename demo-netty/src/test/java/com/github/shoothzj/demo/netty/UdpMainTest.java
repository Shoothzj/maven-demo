package com.github.shoothzj.demo.netty;

import com.github.shoothzj.demo.netty.udp.UdpClient;
import com.github.shoothzj.demo.netty.udp.UdpClientHandler;
import com.github.shoothzj.demo.netty.udp.UdpConstant;
import com.github.shoothzj.demo.netty.udp.UdpServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;

import java.net.InetSocketAddress;


/**
 * @author akka
 */
@Slf4j
public class UdpMainTest {

    private static final String host = "127.0.0.1";

    private static final int port = 18888;

    @Test
    public void udpServer() {
        final UdpServer udpServer = new UdpServer();
        try {
            udpServer.start(host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void udpClient() throws Exception {
		final UdpClient udpClient = new UdpClient();
		udpClient.start();
        int i = 0;
        while (i < 10) {
			udpClient.sendMessage(UdpConstant.CLIENT_REQ, new InetSocketAddress(host, port));
            log.info("{} client send message is: {}", i, UdpConstant.CLIENT_REQ);
            i++;
        }
    }

}