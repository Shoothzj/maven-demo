package com.github.shoothzj.demo.basic;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;

/**
 * @author hezhangjian
 */
@Slf4j
public class ConfConverter {

    private String inputPath;

    private String outputPath;

    private Properties properties;

    public ConfConverter(String inputPath, String outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.properties = new Properties();
    }

    public void init() {
        try {
            properties.load(new FileInputStream(inputPath));
            log.info("properties is [{}]", properties);
        } catch (Exception e) {
            log.error("read file exception is ", e);
        }
    }

    public void modify(Map<String, String> map) {
        map.forEach(new BiConsumer<String, String>() {
            @Override
            public void accept(String s, String s2) {
                properties.put(s, s2);
            }
        });
    }

    public void modify(boolean condition, Map<String, String> map) {
        if (condition) {
            map.forEach(new BiConsumer<String, String>() {
                @Override
                public void accept(String s, String s2) {
                    properties.put(s, s2);
                }
            });
        }
    }

    public void end() {
        try {
            properties.store(new FileOutputStream(outputPath), "");
        } catch (Exception e) {
            log.error("write file exception ", e);
        }
    }


}
