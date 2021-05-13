package com.github.shoothzj.demo.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author hezhangjian
 */
@Slf4j
public class TimeTest {

    @Test
    public void nanoTimeTest() {
        final long nanoTime = System.nanoTime();
        log.info("nano time is {}", nanoTime);
    }

}
