package com.github.shoothzj.demo.netty;

import io.netty.util.internal.PlatformDependent;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * @author hezhangjian
 */
@Slf4j
public class NettyMemoryUsageTest {

    public static void main(String[] args) throws Exception {
        {
            Field field = PlatformDependent.class.getDeclaredField("DIRECT_MEMORY_COUNTER");
            field.setAccessible(true);
            final Object o = field.get(null);
            System.out.println(o);
        }
        {
            final Class<?> className = Class.forName("io.netty.util.internal.PlatformDependent");
            final Field reservedMemory = className.getDeclaredField("DIRECT_MEMORY_COUNTER");
            reservedMemory.setAccessible(true);
            final Object o = reservedMemory.get(null);
            System.out.println(o);
        }
    }

}
