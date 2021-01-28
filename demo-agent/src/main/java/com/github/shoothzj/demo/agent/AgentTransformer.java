package com.github.shoothzj.demo.agent;

import com.github.shoothzj.demo.agent.interceptor.RestControllerInterceptor;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hezhangjian
 */
public class AgentTransformer implements AgentBuilder.Transformer {

    private static final Logger log = LoggerFactory.getLogger(AgentTransformer.class);

    @Override
    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {
        try {
            //包名放在com.github.shoothzj.demo.agent.test.controller下视为controller代码
            if (typeDescription.getTypeName().startsWith("com.github.shoothzj.demo.agent.test.controller")) {
                final Advice advice = Advice.to(RestControllerInterceptor.class);
                return builder.visit(advice
                        .on(ElementMatchers.isAnnotatedWith(RequestMapping.class)
                                .or(ElementMatchers.isAnnotatedWith(GetMapping.class))
                                .or(ElementMatchers.isAnnotatedWith(PostMapping.class))
                                .or(ElementMatchers.isAnnotatedWith(PutMapping.class))
                                .or(ElementMatchers.isAnnotatedWith(DeleteMapping.class))
                                .or(ElementMatchers.isAnnotatedWith(PatchMapping.class))));
            }
        } catch (Exception e) {
            log.error("error is ", e);
        }
        return builder;
    }

}