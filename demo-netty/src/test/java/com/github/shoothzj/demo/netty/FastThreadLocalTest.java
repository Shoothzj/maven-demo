package com.github.shoothzj.demo.netty;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author hezhangjian
 */
@Slf4j
public class FastThreadLocalTest {

    @Test
    public void testFastThreadLocal() throws Exception {
        CountDownLatch cdl = new CountDownLatch(10000);
        FastThreadLocal<String> threadLocal = new FastThreadLocal<String>();
        long starTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            new FastThreadLocalThread(new Runnable() {

                @Override
                public void run() {
                    threadLocal.set(Thread.currentThread().getName());
                    for (int k = 0; k < 100000; k++) {
                        threadLocal.get();
                    }
                    cdl.countDown();
                }
            }, "Thread" + (i + 1)).start();
        }

        cdl.await();
        System.out.println(System.currentTimeMillis() - starTime);
    }
}