package com.github.shoothzj.demo.hc.dis;

import com.huaweicloud.dis.DIS;
import com.huaweicloud.dis.iface.data.request.PutRecordsRequest;
import com.huaweicloud.dis.iface.data.request.PutRecordsRequestEntry;
import com.huaweicloud.dis.iface.data.response.PutRecordsResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author from huawei cloud dis demo
 */
@Slf4j
public class DisDataTest {

    @Test
    public void testUploadData() {
        final DIS dis = DisUtil.getDis();
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
        final PutRecordsResult putRecordsResult = dis.putRecords(putRecordsRequest);
        log.info("put result {}", putRecordsResult);
    }

}
