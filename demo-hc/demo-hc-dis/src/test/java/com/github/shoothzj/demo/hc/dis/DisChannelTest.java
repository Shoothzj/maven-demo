package com.github.shoothzj.demo.hc.dis;

import com.huaweicloud.dis.DIS;
import com.huaweicloud.dis.iface.stream.request.CreateStreamRequest;
import com.huaweicloud.dis.iface.stream.request.DeleteStreamRequest;
import com.huaweicloud.dis.iface.stream.request.DescribeStreamRequest;
import com.huaweicloud.dis.iface.stream.response.CreateStreamResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author hezhangjian
 */
@Slf4j
public class DisChannelTest {

    public static final String STREAM_NAME = "dis-test";

    @Test
    public void createChannel() {
        final DIS dis = DisUtil.getDis();
        final CreateStreamRequest createStreamRequest = new CreateStreamRequest();
        createStreamRequest.setStreamName("dis-test");
        createStreamRequest.setPartitionCount(1);
        final CreateStreamResult createStreamResult = dis.createStream(createStreamRequest);
        log.info("create stream result is {}", createStreamResult);
    }

    @Test
    public void getChannel() {
        final DIS dis = DisUtil.getDis();
        final DescribeStreamRequest describeStreamRequest = new DescribeStreamRequest();
        dis.describeStream(describeStreamRequest);
    }

    @Test
    public void deleteChannel() {
        final DIS dis = DisUtil.getDis();
        final DeleteStreamRequest deleteStreamRequest = new DeleteStreamRequest();
        deleteStreamRequest.setStreamName(STREAM_NAME);
        dis.deleteStream(deleteStreamRequest);
    }

}
