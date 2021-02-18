package com.github.shoothzj.demo.agent.interceptor;

import com.github.shoothzj.demo.agent.util.AgentUtil;
import net.bytebuddy.asm.Advice;

import java.util.concurrent.TimeUnit;

/**
 * @author hezhangjian
 */
public class PulsarServerCnxInterceptor {

    /**
     * #t Class名 ex: org.apache.pulsar.broker.service.ServerCnx
     * #m Method名 ex: handleSubscribe
     *
     * @param signature
     */
    @Advice.OnMethodEnter
    public static void enter(@Advice.Origin("#t #m") String signature) {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AgentUtil.info("[{}]", signature);
    }

}
