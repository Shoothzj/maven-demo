package com.github.shoothzj.demo.nio;

import com.github.shoothzj.javatool.util.LogUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author hezhangjian
 */
@Slf4j
public class TimeBioServer {

    public static void main(String[] args) throws Exception {
        LogUtil.configureLog();
        int port = 8080;
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            log.info("The time server is start in port : {}", port);
            Socket socket = null;
            while (true) {
                socket = server.accept();
                new Thread().start();
            }
        } finally {
            if (server != null) {
                log.info("the time server close");
                server.close();
            }
        }
    }

}
