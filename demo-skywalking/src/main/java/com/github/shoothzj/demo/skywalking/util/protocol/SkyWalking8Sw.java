package com.github.shoothzj.demo.skywalking.util.protocol;

import com.github.shoothzj.demo.skywalking.util.Base64Util;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class SkyWalking8Sw {

    public static void parseProtocol(String[] array) {
        parsePartSample(array[0]);
        parsePartTraceId(array[1]);
        parsePartTraceSegmentId(array[2]);
        parsePartTraceParentSpanId(array[3]);
        parsePartTraceParentService(array[4]);
        parsePartTraceParentServiceInstance(array[5]);
        parsePartTraceParentEndpoint(array[6]);
        parsePartTargetAddress(array[7]);
    }

    /**
     * Sample.
     * 0 or 1. 0 means context exists,
     * but could(most likely will) ignore.
     * 1 means this trace need to be sampled and send to backend.
     */
    public static void parsePartSample(String str) {
        log.info("sample {}", str);
    }

    /**
     * Trace Id.
     * String(BASE64 encoded).
     * Literal String and unique globally.
     * @param str
     */
    public static void parsePartTraceId(String str) {
        log.info("trace id {}", Base64Util.decode(str));
    }

    /**
     * Parent trace segment Id.
     * String(BASE64 encoded).
     * Literal String and unique globally.
     * @param str
     */
    public static void parsePartTraceSegmentId(String str) {
        log.info("segment id {}", Base64Util.decode(str));
    }

    /**
     * Parent span Id.
     * Integer.
     * Begin with 0.
     * This span id points to the parent span in parent trace segment.
     * @param str
     */
    public static void parsePartTraceParentSpanId(String str) {
        log.info("parent span id {}", Base64Util.decode(str));
    }

    /**
     * Parent service.
     * String(BASE64 encoded).
     * The length should not be less or equal than 50 UTF-8 characters.
     * @param str
     */
    public static void parsePartTraceParentService(String str) {
        log.info("parent service {}", Base64Util.decode(str));
    }

    /**
     * Parent service instance.
     * String(BASE64 encoded).
     * The length should be less or equal than 50 UTF-8 characters.
     * @param str
     */
    public static void parsePartTraceParentServiceInstance(String str) {
        log.info("parent service instance {}", Base64Util.decode(str));
    }

    /**
     * Parent endpoint.
     * String(BASE64 encoded).
     * Operation Name of the first entry span in the parent segment.
     * The length should be less than 150 UTF-8 characters.
     * @param str
     */
    public static void parsePartTraceParentEndpoint(String str) {
        log.info("parent endpoint {}", Base64Util.decode(str));
    }

    /**
     * Target address used at client side of this request.
     * String(BASE64 encoded).
     * The network address(not must be IP + port) used at client side to access this target service.
     * @param str
     */
    public static void parsePartTargetAddress(String str) {
        log.info("target address {}", Base64Util.decode(str));
    }

}
