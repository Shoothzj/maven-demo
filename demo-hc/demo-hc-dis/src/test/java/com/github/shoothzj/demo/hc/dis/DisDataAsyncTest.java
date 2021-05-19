package com.github.shoothzj.demo.hc.dis;

import com.github.shoothzj.javatool.util.CommonUtil;
import com.huaweicloud.dis.DISAsync;
import com.huaweicloud.dis.core.handler.AsyncHandler;
import com.huaweicloud.dis.iface.data.request.PutRecordsRequest;
import com.huaweicloud.dis.iface.data.request.PutRecordsRequestEntry;
import com.huaweicloud.dis.iface.data.response.PutRecordsResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author hezhangjian
 */
@Slf4j
public class DisDataAsyncTest {

    @Test
    public void testUploadOneData() {
        long startTime = System.currentTimeMillis();
        final DISAsync disAsync = DisUtil.getDisAsync();
        final PutRecordsRequest putRecordsRequest = new PutRecordsRequest();
        putRecordsRequest.setStreamName("dis-test");
        final PutRecordsRequestEntry putRecordsRequestEntry = new PutRecordsRequestEntry();
        final byte[] bytes = ("hello_world").getBytes(StandardCharsets.UTF_8);
        putRecordsRequestEntry.setData(ByteBuffer.wrap(bytes));
        putRecordsRequestEntry.setPartitionKey(null);
        putRecordsRequest.setRecords(Collections.singletonList(putRecordsRequestEntry));
        disAsync.putRecordsAsync(putRecordsRequest, new AsyncHandler<PutRecordsResult>() {
            @Override
            public void onError(Exception exception) throws Exception {
                log.error("exception ", exception);
            }

            @Override
            public void onSuccess(PutRecordsResult putRecordsResult) throws Exception {
                log.info("async send success {} cost is {}", putRecordsResult, System.currentTimeMillis() - startTime);
            }
        });
        CommonUtil.sleep(TimeUnit.SECONDS, 15);
    }

    @Test
    public void testUploadData() {
        long startTime = System.currentTimeMillis();
        final DISAsync disAsync = DisUtil.getDisAsync();
        final PutRecordsRequest putRecordsRequest = new PutRecordsRequest();
        putRecordsRequest.setStreamName("dis-test");
        List<PutRecordsRequestEntry> putRecordsRequestEntryList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            final PutRecordsRequestEntry putRecordsRequestEntry = new PutRecordsRequestEntry();
            final byte[] bytes = ("hello_world" + i).getBytes(StandardCharsets.UTF_8);
            putRecordsRequestEntry.setData(ByteBuffer.wrap(bytes));
            putRecordsRequestEntryList.add(putRecordsRequestEntry);
        }
        putRecordsRequest.setRecords(putRecordsRequestEntryList);
        disAsync.putRecordsAsync(putRecordsRequest, new AsyncHandler<PutRecordsResult>() {
            @Override
            public void onError(Exception exception) throws Exception {
                log.error("exception ", exception);
            }

            @Override
            public void onSuccess(PutRecordsResult putRecordsResult) throws Exception {
                log.info("async send success {} cost is {}", putRecordsResult, System.currentTimeMillis() - startTime);
            }
        });
        CommonUtil.sleep(TimeUnit.SECONDS, 15);
    }

}
