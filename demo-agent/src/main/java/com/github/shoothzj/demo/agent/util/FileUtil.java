package com.github.shoothzj.demo.agent.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author hezhangjian
 */
public class FileUtil {

    public static String readFile(String file) {
        StringBuilder sb = new StringBuilder();
        try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buf = new byte[1024];
            int num;

            while ((num = is.read(buf)) > 0) {
                sb.append(new String(buf, 0, num, StandardCharsets.UTF_8));
            }

            if (sb.length() == 0) {
                return null;
            }
        } catch (IOException e) {
            AgentUtil.error("Failed to read file", e);
            return null;
        }

        return sb.toString();
    }

}
