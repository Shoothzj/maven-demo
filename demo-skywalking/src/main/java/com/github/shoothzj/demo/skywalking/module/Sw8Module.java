package com.github.shoothzj.demo.skywalking.module;

import lombok.Data;

/**
 * @author hezhangjian
 */
@Data
public class Sw8Module {

    private String sample;

    private String traceId;

    private String traceSegmentId;

    private int parentSpanId;

    private String parentService;

    private String parentServiceInstance;

    private String parentEndpoint;

    private String targetAddress;

    public Sw8Module() {
    }
}
