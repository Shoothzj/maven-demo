package com.github.shoothzj.demo.netty;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * @author hezhangjian
 */
@Slf4j
public class DirectMemoryTest {

    public static void main(String[] args) throws Exception {
        final Class<?> className = Class.forName("java.nio.Bits");
        final Field reservedMemory = className.getDeclaredField("reservedMemory");
        reservedMemory.setAccessible(true);
        final Object o = reservedMemory.get(null);
        System.out.println(o);
    }

}
