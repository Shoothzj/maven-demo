package com.github.shoothzj.demo.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.DeleteInstanceRequest;
import com.aliyuncs.ecs.model.v20140526.DeleteInstanceResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse;
import com.aliyuncs.profile.DefaultProfile;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author hezhangjian
 */
@Slf4j
public class EcsService {

    @SneakyThrows
    public static List<DescribeInstancesResponse.Instance> getEcsList() {
        final DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest();
        final DescribeInstancesResponse response = AliService.client.getAcsResponse(describeInstancesRequest);
        final List<DescribeInstancesResponse.Instance> instances = response.getInstances();
        return instances;
    }

    @SneakyThrows
    public static void deleteAll() {
        final List<DescribeInstancesResponse.Instance> ecsList = getEcsList();
        for (DescribeInstancesResponse.Instance instance : ecsList) {
            final DeleteInstanceRequest deleteInstanceRequest = new DeleteInstanceRequest();
            deleteInstanceRequest.setInstanceId(instance.getInstanceId());
            deleteInstanceRequest.setForce(true);
            final DeleteInstanceResponse deleteInstanceResponse = AliService.client.getAcsResponse(deleteInstanceRequest);
            log.info("response is [{}]", deleteInstanceResponse);
        }
    }

}
