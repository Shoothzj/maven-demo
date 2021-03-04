package com.github.shoothzj.demo.bookkeeper;

import com.github.shoothzj.javatool.util.CommonUtil;
import com.github.shoothzj.javatool.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.bookkeeper.common.util.OrderedExecutor;
import org.apache.bookkeeper.common.util.SafeRunnable;

import java.util.concurrent.TimeUnit;

/**
 * @author hezhangjian
 */
@Slf4j
public class OrderedExecutorTest {

    public static void main(String[] args) {
        LogUtil.configureLog();
        final OrderedExecutor orderedExecutor = OrderedExecutor.newBuilder()
                .numThreads(1)
                .traceTaskExecution(true)
                .traceTaskWarnTimeMicroSec(1)
                .name("bookkeeper-ml-workers")
                .build();
        log.info("start");
        while (true) {
            orderedExecutor.executeOrdered(1, new SafeRunnable() {
                @Override
                public void safeRun() {
                    log.info("run run run");
                    CommonUtil.sleep(TimeUnit.SECONDS, 2);
                }
            });
        }
    }

}
