package com.github.shoothzj.demo.agent.interceptor;

import com.github.shoothzj.demo.agent.util.AgentUtil;
import com.github.shoothzj.demo.agent.util.ZooKeeperUtil;
import net.bytebuddy.asm.Advice;

/**
 * @author hezhangjian
 */
public class ZooKeeperWriteInterceptor {

    /**
     * #t Class名 ex: com.github.shoothzj.demo.agent.test.controller.TestController
     * #m Method名 ex: createJob
     * #d Method描述 ex: (Ljava/lang/String;Lcom/github/shoothzj/demo/agent/test/module/rest/CreateJobReqDto;)Lorg/springframework/http/ResponseEntity;
     * #s 方法签名 ex: (java.lang.String,com.github.shoothzj.demo.agent.test.module.rest.CreateJobReqDto)
     * #r 返回类型 ex: org.springframework.http.ResponseEntity
     *
     * @param signature
     */
    @Advice.OnMethodEnter
    public static void enter(@Advice.Origin("#t #m") String signature) {
        if (ZooKeeperUtil.getWriteError()) {
            throw new IllegalStateException("error " + signature);
        }
        AgentUtil.info("[{}]", signature);
    }

}
