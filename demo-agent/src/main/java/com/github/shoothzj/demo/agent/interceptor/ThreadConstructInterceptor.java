package com.github.shoothzj.demo.agent.interceptor;

import net.bytebuddy.asm.Advice;

/**
 * @author hezhangjian
 */
public class ThreadConstructInterceptor {

    @Advice.OnMethodExit
    public static void intercept(@Advice.This Object inst) throws Exception {
        Thread thread = (Thread) inst;
        String msg = String.format("create thread %s", thread.getName());
        System.out.println(msg);
    }

}
