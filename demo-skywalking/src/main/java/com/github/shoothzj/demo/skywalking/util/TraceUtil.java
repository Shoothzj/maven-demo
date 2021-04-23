package com.github.shoothzj.demo.skywalking.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;

/**
 * @author hezhangjian
 */
@Slf4j
public class TraceUtil {

    public void print() {
        final String traceId = TraceContext.traceId();
        final String segmentId = TraceContext.segmentId();
        final int spanId = TraceContext.spanId();
    }

}
