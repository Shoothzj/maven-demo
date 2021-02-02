package com.github.shoothzj.demo.agent.interceptor;

import com.github.shoothzj.demo.agent.config.ZooKeeperConfigHolder;
import com.github.shoothzj.demo.agent.util.AgentUtil;
import com.github.shoothzj.demo.agent.config.ZooKeeperConfig;
import net.bytebuddy.asm.Advice;

/**
 * @author hezhangjian
 */
public class ZooKeeperWriteInterceptor {

    /**
     * #t Class名 ex: org.apache.zookeeper.server.PrepRequestProcessor
     * #m Method名 ex: pRequest2Txn
     *
     * @param signature
     */
    @Advice.OnMethodEnter
    public static void enter(@Advice.Origin("#t #m") String signature) {
        if (!ZooKeeperConfigHolder.isWritable()) {
            throw new IllegalStateException("error " + signature);
        }
        AgentUtil.info("[{}]", signature);
    }

}
