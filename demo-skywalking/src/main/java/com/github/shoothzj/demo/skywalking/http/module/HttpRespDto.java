package com.github.shoothzj.demo.skywalking.http.module;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

/**
 * @author hezhangjian
 */
@Data
public class HttpRespDto {

    private Instant processTime;

    public HttpRespDto() {
    }
}
