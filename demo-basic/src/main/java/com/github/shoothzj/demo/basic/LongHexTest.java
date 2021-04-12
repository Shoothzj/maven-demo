package com.github.shoothzj.demo.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author hezhangjian
 */
@Slf4j
public class LongHexTest {

    @Test
    public void hex2Long() {
        final Long result = Long.decode("0x11");
        log.info("result is [{}]", result);
    }

}
