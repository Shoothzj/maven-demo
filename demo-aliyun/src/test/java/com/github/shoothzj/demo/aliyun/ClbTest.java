package com.github.shoothzj.demo.aliyun;

import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancersRequest;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancersResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

/**
 * @author hezhangjian
 */
@Slf4j
public class ClbTest {

    @Test
    public void getClb() throws Exception {
        final List<DescribeLoadBalancersResponse.LoadBalancer> balancers = ClbService.getClb();
        log.info("response is [{}]", balancers);
    }

    @Test
    public void deleteAll() throws Exception {
        ClbService.deleteAll();
    }

}
