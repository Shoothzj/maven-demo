package com.github.shoothzj.demo.skywalking.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.util.Base64;


/**
 * @author hezhangjian
 */
@Slf4j
public class Base64Util {

    static final Base64 base64 = new Base64();

    public static String decode(String str) {
        return new String(base64.decode(str));
    }

    public static byte[] decode2Bytes(String str) {
        return base64.decode(str);
    }

}
