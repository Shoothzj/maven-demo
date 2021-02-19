package com.github.shoothzj.demo.nio;

import com.github.shoothzj.javatool.util.StreamUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author hezhangjian
 */
@Slf4j
public class TimeBioClient {

    public static void main(String[] args) {
        int port = 8080;
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket("127.0.0.1", port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            log.info("send order 2 server succeed.");
            final String resp = in.readLine();
            log.info("Now is : {}", resp);
        } catch (Exception e) {
            // do nothing
        } finally {
            StreamUtil.close(out);
            StreamUtil.close(in);
            StreamUtil.close(socket);
        }
    }

}
