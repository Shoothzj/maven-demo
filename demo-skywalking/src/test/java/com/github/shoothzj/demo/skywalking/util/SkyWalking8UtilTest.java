package com.github.shoothzj.demo.skywalking.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author hezhangjian
 */
@Slf4j
public class SkyWalking8UtilTest {

    @Test
    public void testSw8() {
        String sourceStr = "49, 45, 79, 87, 85, 120, 78, 109, 77, 119, 90, 71, 90, 108, 89, 109, 90, 106, 78, 71, 69, 122, 77, 109, 69, 120, 77, 87, 78, 105, 78, 106, 107, 52, 78, 50, 69, 121, 78, 106, 104, 105, 77, 109, 69, 117, 78, 106, 73, 117, 77, 84, 89, 120, 79, 84, 65, 52, 77, 122, 81, 50, 77, 122, 69, 122, 78, 106, 65, 120, 77, 122, 69, 61, 45, 79, 87, 85, 120, 78, 109, 77, 119, 90, 71, 90, 108, 89, 109, 90, 106, 78, 71, 69, 122, 77, 109, 69, 120, 77, 87, 78, 105, 78, 106, 107, 52, 78, 50, 69, 121, 78, 106, 104, 105, 77, 109, 69, 117, 78, 106, 73, 117, 77, 84, 89, 120, 79, 84, 65, 52, 77, 122, 81, 50, 77, 122, 69, 122, 78, 106, 65, 120, 77, 122, 65, 61, 45, 49, 45, 83, 72, 82, 48, 99, 65, 61, 61, 45, 78, 106, 104, 105, 78, 68, 73, 53, 89, 122, 77, 53, 90, 84, 100, 105, 78, 71, 73, 48, 78, 50, 69, 49, 90, 106, 77, 119, 90, 68, 99, 120, 77, 68, 108, 106, 90, 106, 81, 51, 77, 122, 86, 65, 78, 121, 52, 121, 78, 68, 107, 117, 77, 106, 107, 117, 77, 84, 89, 61, 45, 101, 49, 66, 80, 85, 49, 82, 57, 76, 50, 49, 122, 90, 119, 61, 61, 45, 98, 71, 57, 106, 89, 87, 120, 111, 98, 51, 78, 48, 79, 106, 107, 119, 79, 84, 73, 61";
        final String[] splitArray = sourceStr.split(",");
        byte[] bytes = new byte[splitArray.length];
        for (int i = 0; i < splitArray.length; i++) {
            final String trim = splitArray[i].trim();
            bytes[i] = Byte.parseByte(trim);
        }
        SkyWalking8Util.parseSw(bytes);
    }

    @Test
    public void testSw8x() {
        String sourceStr = "48, 45, 49, 54, 49, 57, 48, 57, 50, 52, 51, 54, 53, 56, 53";
        final String[] splitArray = sourceStr.split(",");
        byte[] bytes = new byte[splitArray.length];
        for (int i = 0; i < splitArray.length; i++) {
            final String trim = splitArray[i].trim();
            bytes[i] = Byte.parseByte(trim);
        }
        SkyWalking8Util.parseSwX(bytes);
    }

}
