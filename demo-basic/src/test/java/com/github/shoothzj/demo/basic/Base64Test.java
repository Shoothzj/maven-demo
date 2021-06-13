package com.github.shoothzj.demo.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Base64;

/**
 * @author hezhangjian
 */
@Slf4j
public class Base64Test {

    @Test
    public void base64Encode() {
        Base64.getEncoder().encode(new byte[]{12, 24, 35});
    }

    @Test
    public void base64Decode() {
        final byte[] bytes = Base64.getEncoder().encode(new byte[]{12, 24, 35});
        final String result = new String(bytes);
        System.out.println(result);
        final byte[] decode = Base64.getDecoder().decode(result);
        for (byte b : decode) {
            log.info("byte is {}", b);
        }
    }

}
