package com.github.shoothzj.demo.agent.interceptor;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.shoothzj.demo.agent.util.AgentJacksonUtil;
import com.github.shoothzj.demo.agent.util.AgentUtil;
import net.bytebuddy.asm.Advice;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Method;

/**
 * @author hezhangjian
 */
public class RestControllerInterceptor {

    private static final ThreadLocal<Long> costThreadLocal = new ThreadLocal<>();

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
        AgentUtil.info("[{}]", signature);
    }

    /**
     * @param method 方法名
     * @param args
     * @param result
     * @param thrown
     */
    @SuppressWarnings("rawtypes")
    @Advice.OnMethodExit(onThrowable = Throwable.class)
    public static void exit(@Advice.Origin Method method, @Advice.AllArguments Object[] args,
                            @Advice.Return Object result, @Advice.Thrown Throwable thrown) {
        AgentUtil.debug("method is [{}]", method);
        final ArrayNode arrayNode = AgentJacksonUtil.createArrayNode();
        for (Object arg : args) {
            arrayNode.add(AgentJacksonUtil.toJson(arg));
        }
        final ObjectNode objectNode = AgentJacksonUtil.createObjectNode();
        objectNode.set("args", arrayNode);
        ResponseEntity responseEntity = (ResponseEntity) result;
        if (thrown == null) {
            AgentUtil.info("status code is [{}] args is [{}] result is [{}]", responseEntity.getStatusCode(), objectNode, responseEntity.getBody());
        } else {
            AgentUtil.info("status code is exception is ", thrown);
        }
    }


}
