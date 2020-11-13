package com.github.shoothzj.spring.async.controller;

import com.github.shoothzj.javatool.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author hezhangjian
 * @see "https://juejin.im/post/6844903621826445319"
 */
@Slf4j
@RestController
public class WebAsyncTaskController {

    @GetMapping(path = "/webAsyncTask/normal")
    public WebAsyncTask<String> handleReqDefResultNormal() {
        log.info("received async deferred result request");
        final WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(10 * 1000L, () -> {
            log.info("异步线程处理");
            CommonUtil.sleep(TimeUnit.SECONDS, 5);
            return UUID.randomUUID().toString();
        });
        webAsyncTask.onCompletion(() -> log.info("任务执行完成"));
        log.info("继续处理其他事情");
        return webAsyncTask;
    }


}
