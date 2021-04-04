package com.github.shoothzj.demo.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author hezhangjian
 */
@Slf4j
public class DemoSocketSend {

    String ip = "127.0.0.1";

    int port = 8000;

    @Test
    public void testSocketSend() throws Exception {
        Socket socket = new Socket(ip, port);
        socket.setTcpNoDelay(true);
        final DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.write("HelloWorld".getBytes(StandardCharsets.UTF_8));
        socket.shutdownOutput();
        dataOutputStream.flush();
    }

}
