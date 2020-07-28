package com.github.shoothzj.demo.config;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author hezhangjian
 */
public class Main {

    public static void main(String[] args) throws Exception {
        InputStream asStream = Main.class.getResourceAsStream("/config/test.config");
        System.out.println(asStream);
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (asStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }
        System.out.println(textBuilder);
    }

}