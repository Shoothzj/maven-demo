package com.github.shoothzj.demo.aliyun;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author hezhangjian
 */
@Slf4j
public class AkSkReaderTest {

    @Test
    public void testGetAli() {
        final Ali ali = AkSkReader.getAli();
        log.info("ali is [{}]", ali);
    }

}
