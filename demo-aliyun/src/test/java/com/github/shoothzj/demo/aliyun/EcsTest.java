package com.github.shoothzj.demo.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

/**
 * @author hezhangjian
 */
@Slf4j
public class EcsTest {

    @Test
    public void describeInstance() throws Exception {
        final List<DescribeInstancesResponse.Instance> ecsList = EcsService.getEcsList();
        for (DescribeInstancesResponse.Instance instance : ecsList) {
            log.info("instance is [{}]", instance);
        }
    }

    @Test
    public void deleteInstances() throws Exception {
        EcsService.deleteAll();
    }

}
