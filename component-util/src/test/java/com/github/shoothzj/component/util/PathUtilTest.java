package com.github.shoothzj.component.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author hezhangjian
 */
@Slf4j
public class PathUtilTest {

    @Test
    public void testPrintPath() {
        log.info("{}", PathUtil.getResourcesPath());
    }

}