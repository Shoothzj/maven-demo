package com.github.shoothzj.demo.iotda;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class Sha256MacTest {


    @Test
    public void testSha256() {
        final String deviceMessage = Sha256Mac.sha256Mac("deviceMessage", TimeUtil.getTimeStamp());
        log.info("device message is {}", deviceMessage);
    }

}