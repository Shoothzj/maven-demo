package com.github.shoothzj.demo.skywalking.util;

import com.github.shoothzj.demo.skywalking.util.protocol.SkyWalking8Sw;
import com.github.shoothzj.demo.skywalking.util.protocol.SkyWalking8Swx;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class SkyWalking8Util {

    public static void parseSw(byte[] value) {
        String result = new String(value);
        final String[] split = result.split("-");
        SkyWalking8Sw.parseProtocol(split);
    }

    public static void parseSw(String value) {
        final String[] split = value.split("-");
        SkyWalking8Sw.parseProtocol(split);
    }

    public static void parseSwX(byte[] value) {
        String result = new String(value);
        final String[] split = result.split("-");
        SkyWalking8Swx.parseProtocol(split);
    }

    public static void parseSwX(String value) {
        final String[] split = value.split("-");
        SkyWalking8Swx.parseProtocol(split);
    }

    public static void parseSwCorr(byte[] value) {

    }

    public static void parseSwCorr(String value) {

    }

}
