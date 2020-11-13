package com.github.shoothzj.spring.async.controller;

import com.github.shoothzj.javatool.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * @author hezhangjian
 */
@Slf4j
@RestController
public class DeferredResultController {

    @GetMapping(path = "/deferResult/normal")
    public DeferredResult<ResponseEntity<String>> handleReqDefResultNormal() {
        log.info("received async deferred result request");
        DeferredResult<ResponseEntity<String>> output = new DeferredResult<>();
        ForkJoinPool.commonPool().submit(() -> {
            log.info("processing in separate thread");
            CommonUtil.sleep(TimeUnit.SECONDS, 5);
            output.setResult(ResponseEntity.ok("ok"));
        });
        log.info("servlet thread freed");
        return output;
    }

    @GetMapping(path = "/deferResult/failure")
    public DeferredResult<ResponseEntity<String>> handleReqDefResultFail() {
        log.info("received async deferred result request");
        DeferredResult<ResponseEntity<String>> output = new DeferredResult<>();
        ForkJoinPool.commonPool().submit(() -> {
            log.info("processing in separate thread");
            CommonUtil.sleep(TimeUnit.SECONDS, 5);
            output.setErrorResult(ResponseEntity.ok("ok"));
        });
        log.info("servlet thread freed");
        return output;
    }



}
