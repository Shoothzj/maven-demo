package com.github.shoothzj.demo.netty;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author hezhangjian
 */
@Slf4j
public class ThreadLocalTest {

    @Test
    public void testThreadLocal() throws Exception {
        CountDownLatch cdl = new CountDownLatch(10_000);
        ThreadLocal<String> threadLocal = new ThreadLocal<String>();
        long starTime = System.currentTimeMillis();
        for (int i = 0; i < 10_000; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    threadLocal.set(Thread.currentThread().getName());
                    for (int k = 0; k < 100_000; k++) {
                        threadLocal.get();
                    }
                    cdl.countDown();
                }
            }, "Thread" + (i + 1)).start();
        }
        cdl.await();
        System.out.println(System.currentTimeMillis() - starTime + "ms");
    }

}
