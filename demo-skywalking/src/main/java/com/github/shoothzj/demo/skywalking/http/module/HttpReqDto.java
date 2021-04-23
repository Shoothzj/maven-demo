package com.github.shoothzj.demo.skywalking.http.module;

import lombok.Data;

/**
 * @author hezhangjian
 */
@Data
public class HttpReqDto {

    private MsgType msgType;

    private String msg;

    public HttpReqDto() {
    }
}
