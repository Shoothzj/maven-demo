package com.github.shoothzj.demo.skywalking.util.protocol;

import com.github.shoothzj.demo.skywalking.module.Sw8Module;
import com.github.shoothzj.demo.skywalking.util.Base64Util;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class SkyWalking8Sw {

    public static Sw8Module parseProtocol(String[] array) {
        Sw8Module sw8Module = new Sw8Module();
        sw8Module.setSample(parsePartSample(array[0]));
        sw8Module.setTraceId(parsePartTraceId(array[1]));
        sw8Module.setTraceSegmentId(parsePartTraceSegmentId(array[2]));
        sw8Module.setParentSpanId(parsePartTraceParentSpanId(array[3]));
        sw8Module.setParentService(parsePartTraceParentService(array[4]));
        sw8Module.setParentServiceInstance(parsePartTraceParentServiceInstance(array[5]));
        sw8Module.setParentEndpoint(parsePartTraceParentEndpoint(array[6]));
        sw8Module.setTargetAddress(parsePartTargetAddress(array[7]));
        return sw8Module;
    }

    /**
     * Sample.
     * 0 or 1. 0 means context exists,
     * but could(most likely will) ignore.
     * 1 means this trace need to be sampled and send to backend.
     * @return
     */
    public static String parsePartSample(String str) {
        log.debug("sample {}", str);
        return str;
    }

    /**
     * Trace Id.
     * String(BASE64 encoded).
     * Literal String and unique globally.
     * @param str
     * @return
     */
    public static String parsePartTraceId(String str) {
        log.debug("trace id {}", Base64Util.decode(str));
        return str;
    }

    /**
     * Parent trace segment Id.
     * String(BASE64 encoded).
     * Literal String and unique globally.
     * @param str
     * @return
     */
    public static String parsePartTraceSegmentId(String str) {
        log.debug("segment id {}", Base64Util.decode(str));
        return str;
    }

    /**
     * Parent span Id.
     * Integer.
     * Begin with 0.
     * This span id points to the parent span in parent trace segment.
     * @param str
     * @return
     */
    public static int parsePartTraceParentSpanId(String str) {
        log.debug("parent span id {}", str);
        return Integer.parseInt(str);
    }

    /**
     * Parent service.
     * String(BASE64 encoded).
     * The length should not be less or equal than 50 UTF-8 characters.
     * @param str
     * @return
     */
    public static String parsePartTraceParentService(String str) {
        log.debug("parent service {}", Base64Util.decode(str));
        return str;
    }

    /**
     * Parent service instance.
     * String(BASE64 encoded).
     * The length should be less or equal than 50 UTF-8 characters.
     * @param str
     * @return
     */
    public static String parsePartTraceParentServiceInstance(String str) {
        log.debug("parent service instance {}", Base64Util.decode(str));
        return str;
    }

    /**
     * Parent endpoint.
     * String(BASE64 encoded).
     * Operation Name of the first entry span in the parent segment.
     * The length should be less than 150 UTF-8 characters.
     * @param str
     * @return
     */
    public static String parsePartTraceParentEndpoint(String str) {
        log.debug("parent endpoint {}", Base64Util.decode(str));
        return str;
    }

    /**
     * Target address used at client side of this request.
     * String(BASE64 encoded).
     * The network address(not must be IP + port) used at client side to access this target service.
     * @param str
     * @return
     */
    public static String parsePartTargetAddress(String str) {
        log.debug("target address {}", Base64Util.decode(str));
        return str;
    }

}
