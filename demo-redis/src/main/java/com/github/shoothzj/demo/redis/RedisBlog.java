package com.github.shoothzj.demo.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author hezhangjian
 */
@Slf4j
@Service
public class RedisBlog {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void secKillHandle(String secKillId) {
        String key = "key:" + secKillId;
        try {
            Boolean lockFlag = redisTemplate.opsForValue().setIfAbsent(key, "val", 10, TimeUnit.SECONDS);
            if (lockFlag) {
                // HTTP请求用户服务进行用户相关的校验
                // 用户活动校验

                // 库存校验
                Object stock = redisTemplate.opsForHash().get(key + ":info", "stock");
                assert stock != null;
                if (Integer.parseInt(stock.toString()) <= 0) {
                    // 业务异常
                } else {
                    redisTemplate.opsForHash().increment(key + ":info", "stock", -1);
                    // 生成订单
                    // 发布订单创建成功事件
                    // 构建响应VO
                }
            }
        } finally {
            // 释放锁
            redisTemplate.delete("key");
            // 构建响应VO
        }
    }


}
