package com.github.shoothzj.demo.aliyun;

import com.aliyuncs.ecs.model.v20140526.CreateInstanceRequest;
import com.aliyuncs.slb.model.v20140515.CreateLoadBalancerRequest;
import com.aliyuncs.slb.model.v20140515.CreateLoadBalancerResponse;
import com.aliyuncs.slb.model.v20140515.DeleteLoadBalancerRequest;
import com.aliyuncs.slb.model.v20140515.DeleteLoadBalancerResponse;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancersRequest;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancersResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author hezhangjian
 */
@Slf4j
public class ClbService {

    @SneakyThrows
    public static void createClb() {
        final CreateLoadBalancerRequest createLoadBalancerRequest = new CreateLoadBalancerRequest();
        final CreateLoadBalancerResponse loadBalancerResponse = AliService.client.getAcsResponse(createLoadBalancerRequest);
        log.info("[{}]", loadBalancerResponse);
    }

    @SneakyThrows
    public static List<DescribeLoadBalancersResponse.LoadBalancer> getClb() {
        final DescribeLoadBalancersRequest describeLoadBalancersRequest = new DescribeLoadBalancersRequest();
        final DescribeLoadBalancersResponse balancersResponse = AliService.client.getAcsResponse(describeLoadBalancersRequest);
        final List<DescribeLoadBalancersResponse.LoadBalancer> loadBalancers = balancersResponse.getLoadBalancers();
        return loadBalancers;
    }

    @SneakyThrows
    public static void deleteAll() {
        final List<DescribeLoadBalancersResponse.LoadBalancer> loadBalancers = getClb();
        for (DescribeLoadBalancersResponse.LoadBalancer loadBalancer : loadBalancers) {
            final DeleteLoadBalancerRequest deleteLoadBalancerRequest = new DeleteLoadBalancerRequest();
            deleteLoadBalancerRequest.setLoadBalancerId(loadBalancer.getLoadBalancerId());
            final DeleteLoadBalancerResponse deleteLoadBalancerResponse = AliService.client.getAcsResponse(deleteLoadBalancerRequest);
            log.info("delete response is {}", deleteLoadBalancerResponse);
        }
    }

}
