package com.github.shoothzj.demo.netty;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author hezhangjian
 */
@Slf4j
public class ThreadLocalSingleThreadTest {

    @Test
    public void testThreadLocal() throws Exception {
        CountDownLatch cdl = new CountDownLatch(1);
        int size = 10000;
        ThreadLocal<String> tls[] = new ThreadLocal[size];
        for (int i = 0; i < size; i++) {
            tls[i] = new ThreadLocal<String>();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                long starTime = System.currentTimeMillis();
                for (int i = 0; i < size; i++) {
                    tls[i].set("value" + i);
                }
                for (int i = 0; i < size; i++) {
                    for (int k = 0; k < 100000; k++) {
                        tls[i].get();
                    }
                }
                System.out.println(System.currentTimeMillis() - starTime + "ms");
                cdl.countDown();
            }
        }).start();
        cdl.await();
    }

}
