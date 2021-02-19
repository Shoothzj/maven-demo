package com.github.shoothzj.demo.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author hezhangjian
 */
@Slf4j
public class TimeNioServer {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        final MultiplexerTimeServer multiplexerTimeServer = new MultiplexerTimeServer(port);
        new Thread(multiplexerTimeServer, "NIO-time-server").start();
    }


}
