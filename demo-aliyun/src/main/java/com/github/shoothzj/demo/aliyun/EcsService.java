package com.github.shoothzj.demo.aliyun;

import com.aliyuncs.ecs.model.v20140526.CreateInstanceRequest;
import com.aliyuncs.ecs.model.v20140526.DeleteInstanceRequest;
import com.aliyuncs.ecs.model.v20140526.DeleteInstanceResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse;
import com.aliyuncs.ecs.model.v20140526.RunInstancesRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author hezhangjian
 */
@Slf4j
public class EcsService {

    @SneakyThrows
    public static void createEcsList() {
        final CreateInstanceRequest createInstanceRequest = new CreateInstanceRequest();
        createInstanceRequest.setImageId("ubuntu_20_04_x64_20G_alibase_20210318.vhd");
        createInstanceRequest.setZoneId("cn-hongkong-b");
        createInstanceRequest.setInstanceType("ecs.g6e.xlarge");
        createInstanceRequest.setSystemDiskCategory("cloud_essd");
        createInstanceRequest.setInstanceChargeType("PostPaid");
        createInstanceRequest.setInternetChargeType("PayByTraffic");
        AliService.client.getAcsResponse(createInstanceRequest);
    }

    @SneakyThrows
    public static void runEcsList() {
        final RunInstancesRequest runInstancesRequest = new RunInstancesRequest();
        //network
        runInstancesRequest.setSecurityGroupId("sg-j6c4i2wnjsyb2vmy8mza");
        runInstancesRequest.setVSwitchId(AliService.ali.getSwitchId());

        runInstancesRequest.setZoneId("cn-hongkong-b");
        runInstancesRequest.setImageId("ubuntu_20_04_x64_20G_alibase_20210318.vhd");
        runInstancesRequest.setInstanceType("ecs.g6e.xlarge");
        runInstancesRequest.setSystemDiskCategory("cloud_essd");
        runInstancesRequest.setInstanceChargeType("PostPaid");
        runInstancesRequest.setInternetChargeType("PayByTraffic");

        //系统配置
        runInstancesRequest.setPassword(AliConfigReader.getAli().getEcsPassword());
        runInstancesRequest.setInstanceName("kubernetes");
        runInstancesRequest.setHostName("kubernetes");

        //network
        runInstancesRequest.setInternetMaxBandwidthOut(1);

        AliService.client.getAcsResponse(runInstancesRequest);
    }

    @SneakyThrows
    public static void createLowEcs() {
        final CreateInstanceRequest createInstanceRequest = new CreateInstanceRequest();
        createInstanceRequest.setImageId("ubuntu_20_04_x64_20G_alibase_20210318.vhd");
        createInstanceRequest.setZoneId("cn-hongkong-b");
        createInstanceRequest.setInstanceType("ecs.g6e.large");
        createInstanceRequest.setSystemDiskCategory("");
        createInstanceRequest.setInstanceChargeType("PostPaid");
        createInstanceRequest.setInternetChargeType("PayByTraffic");
        AliService.client.getAcsResponse(createInstanceRequest);
    }

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
