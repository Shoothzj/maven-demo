package com.github.shoothzj.demo.nio;

import com.github.shoothzj.javatool.util.StreamUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;

/**
 * @author hezhangjian
 */
@Slf4j
public class TimeServerHandler implements Runnable {

    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(), true);

            String body = null;
            while (true) {
                body = in.readLine();
                if (body == null) {
                    break;
                }
                log.info("The time server receive order {}", body);
                String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? LocalDateTime.now().toString() : "BAD ORDER";
                out.println(currentTime);
            }
        } catch (Exception e) {
            StreamUtil.close(in);
            StreamUtil.close(out);
            StreamUtil.close(socket);
        }
    }
}
