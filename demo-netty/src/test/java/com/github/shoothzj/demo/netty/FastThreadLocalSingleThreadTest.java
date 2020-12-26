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
public class FastThreadLocalSingleThreadTest {

    @Test
    public void testThreadLocal() throws Exception {
        CountDownLatch cdl = new CountDownLatch(1);
        int size = 10000;
        FastThreadLocal<String> tls[] = new FastThreadLocal[size];
        for (int i = 0; i < size; i++) {
            tls[i] = new FastThreadLocal<String>();
        }

        new FastThreadLocalThread(new Runnable() {

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
