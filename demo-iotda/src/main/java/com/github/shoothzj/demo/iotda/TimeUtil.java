package com.github.shoothzj.demo.iotda;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author hezhangjian
 */
@Slf4j
public class TimeUtil {

    /***
     * 要求：10位数字
     *
     * @return
     */
    public static String getTimeStamp() {
        String timeStamp = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"))
                .format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
        return timeStamp;
    }

}
