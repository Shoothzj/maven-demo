package com.github.shoothzj.demo.skywalking.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.network.language.agent.v3.SegmentObject;
import org.junit.Test;

@Slf4j
public class EsSegmentBinaryTest {

    @Test
    public void test() throws Exception {
        String str = "CjVlZjYwMGFlOWFmMDg0ZTE0YjFiZTYxNmI3MjVkZjJmNC4zNS4xNjE5NjE2NjY1MDMwMDAwMRI0N2M3NjkzNDMyNDBiNGU0M2FhMjBhMWUyNDBhYTRmZWIuMS4xNjE5NjE2NjgzNDU1MDAwMBrbAhD///////////8BGJ2rtcWRLyDGq7XFkS8qyQESNWVmNjAwYWU5YWYwODRlMTRiMWJlNjE2YjcyNWRmMmY0LjM1LjE2MTk2MTY2NjUwMzAwMDAxGjVlZjYwMGFlOWFmMDg0ZTE0YjFiZTYxNmI3MjVkZjJmNC4zNS4xNjE5NjE2NjY1MDMwMDAwMCoEUENLUDIuNmZjZGFhZWE1ZDk5NDNmMmFmNWZmZmE5NDNjMGY2YzBAMTkyLjE2OC4xLjEwNzoTS2Fma2EvZGVtby9Qcm9kdWNlckIObG9jYWxob3N0OjkwOTIyIkthZmthL2RlbW8vQ29uc3VtZXIvY29uc3VtZXJHcm91cDFIBFApYhsKCW1xLmJyb2tlchIObG9jYWxob3N0OjkwOTJiEAoIbXEudG9waWMSBGRlbW9iHQoUdHJhbnNtaXNzaW9uLmxhdGVuY3kSBTE4NDMxIg1LYWZrYUNvbnN1bWVyKi40ZTE5YmJjMzkzNDc0Yzg1OTFlZjNkNGEzZmVhZGVkZUAxOTIuMTY4LjEuMTA3";
        byte[] decode = Base64Util.decode2Bytes(str);
        SegmentObject segmentObject = SegmentObject.parseFrom(decode);
        log.info("segment obj is {}", segmentObject);
    }

}
