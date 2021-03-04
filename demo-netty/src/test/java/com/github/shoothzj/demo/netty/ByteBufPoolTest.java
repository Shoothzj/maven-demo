package com.github.shoothzj.demo.netty;

import com.github.shoothzj.javatool.util.LogUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

/**
 * @author hezhangjian
 */
@Slf4j
public class ByteBufPoolTest {

    @Before
    public void init() {
        LogUtil.configureLog();
    }

    @Test
    public void testAllocatedNoPool() {
        final PooledByteBufAllocator allocator = new PooledByteBufAllocator(false);
        final ByteBuf byteBuf = allocator.heapBuffer(4);
    }

    /**
     * mac cost 57344ms
     */
    @Test
    public void unPoolTest() {
        long beginTime = System.currentTimeMillis();
        ByteBuf buf = null;
        int maxTimes = 100_000_000;
        for (int i = 0; i < maxTimes; i++) {
            buf = Unpooled.buffer(10 * 1024);
            buf.release();
        }
        log.info("cost is [{}]", System.currentTimeMillis() - beginTime);
    }

    /**
     * mac cost 9030ms
     */
    @Test
    public void poolTest() {
        final PooledByteBufAllocator allocator = new PooledByteBufAllocator(false);
        long beginTime = System.currentTimeMillis();
        ByteBuf buf = null;
        int maxTimes = 100_000_000;
        for (int i = 0; i < maxTimes; i++) {
            buf = allocator.heapBuffer(10 * 1024);
            buf.release();
        }
        log.info("cost is [{}]", System.currentTimeMillis() - beginTime);
    }

}
