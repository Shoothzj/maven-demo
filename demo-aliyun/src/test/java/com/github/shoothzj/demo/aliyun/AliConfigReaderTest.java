package com.github.shoothzj.demo.aliyun;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author hezhangjian
 */
@Slf4j
public class AliConfigReaderTest {

    @Test
    public void testGetAli() {
        final Ali ali = AliConfigReader.getAli();
        log.info("ali is [{}]", ali);
    }

}
