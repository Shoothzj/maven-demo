package com.github.shoothzj.demo.skywalking.util.protocol;

import com.github.shoothzj.demo.skywalking.util.Base64Util;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

/**
 * @author hezhangjian
 */
@Slf4j
public class SkyWalking8Swx {

    public static void parseProtocol(String[] array) {
        parsePartTracingMode(array[0]);
        parsePartTimestamp(array[1]);
    }

    /**
     * Tracing Mode.
     * empty, 0 or 1.
     * empty or 0 is default.
     * 1 represents all spans generated in this context should skip analysis, spanObject#skipAnalysis=true.
     * This context should be propagated to upstream in the default, unless it is changed in the tracing process.
     */
    public static void parsePartTracingMode(String str) {
        log.info("tracing mode {}", str);
    }

    /**
     * The timestamp of sending at the client-side.
     * This is used in async RPC such as MQ.
     * Once it is set, the consumer side would calculate the latency between sending and receiving,
     * and tag the latency in the span by using key transmission.latency automatically.
     *
     * @param str
     */
    public static void parsePartTimestamp(String str) {
        LocalDateTime triggerTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(str)), TimeZone.getDefault().toZoneId());
        log.info("trigger time {}", triggerTime);
    }

}
