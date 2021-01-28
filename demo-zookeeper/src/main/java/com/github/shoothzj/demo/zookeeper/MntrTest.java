package com.github.shoothzj.demo.zookeeper;

import com.github.shoothzj.javatool.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.LineIterator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author hezhangjian
 */
@Slf4j
public class MntrTest {

    private static final String COMMAND = "mntr";

    public static void main(String[] args) {
        LogUtil.configureLog();
        final StringBuilder sb = new StringBuilder(2048);
        BufferedReader bfReader = null;
        OutputStream output = null;
        Socket client = null;
        try {
            client = new Socket("127.0.0.1", 2181);
            client.setSoTimeout(10_000);
            output = client.getOutputStream();
            output.write(COMMAND.getBytes(StandardCharsets.UTF_8));
            output.flush();

            bfReader = new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
            final LineIterator lineIterator = new LineIterator(bfReader);
            String line;
            while (lineIterator.hasNext()) {
                line = lineIterator.next();
//                if (line.length() < 128 && sb.length() < 2048) {
                    sb.append(line).append("").append("\n");
//                }
            }
            log.info("zk line [{}]", sb.toString());
        } catch (IOException e) {

        }
    }

}
