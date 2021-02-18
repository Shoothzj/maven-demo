package com.github.shoothzj.demo.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author hezhangjian
 */
@Slf4j
public class LongDecodeTest {

    @Test
    public void decodeTest() {
        final Long aLong = Long.decode("0x017752E32F99");
        System.out.println(aLong);
    }

}
