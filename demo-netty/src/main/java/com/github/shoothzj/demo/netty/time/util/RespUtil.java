package com.github.shoothzj.demo.netty.time.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @author hezhangjian
 */
@Slf4j
public class RespUtil {

    public static String calResp(String req) {
        return "QUERY TIME ORDER".equalsIgnoreCase(req) ? LocalDateTime.now().toString() : "BAD ORDER";
    }

}
