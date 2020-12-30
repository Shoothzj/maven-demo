package com.github.shoothzj.demo.agent;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.matcher.ElementMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;

/**
 * @author hezhangjian
 */
public class AgentMain {

    private static final Logger log = LoggerFactory.getLogger(AgentMain.class);

    /**
     * call on -javaagnet
     * @param agentArgs
     * @param inst
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("start agent premain");
        final ByteBuddy byteBuddy = new ByteBuddy();
        new AgentBuilder.Default(byteBuddy)
                //这些类都是常见的无需切面注入的类,忽略掉可以提升agent加载速度
                .ignore(nameStartsWith("net.bytebuddy.")
                        .or(nameStartsWith("org.slf4j.")
                        .or(nameStartsWith("org.apache.logging.")
                        .or(nameStartsWith("org.groovy."))
                        .or(nameStartsWith("javassist"))
                        .or(nameStartsWith(".asm."))
                        .or(nameStartsWith("sun.reflect"))
                        .or(ElementMatchers.isSynthetic()))))
                //你想切面的包名
                .type(ElementMatchers.named("com.github.shoothzj"))
                .transform(new AgentTransformer())
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .installOn(inst);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("start agent main");
    }

}
