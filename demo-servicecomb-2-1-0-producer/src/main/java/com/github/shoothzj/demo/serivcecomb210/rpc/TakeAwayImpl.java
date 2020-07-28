package com.github.shoothzj.demo.serivcecomb210.rpc;

import com.github.shoothzj.demo.serivcecomb210.module.RegionEnum;
import com.github.shoothzj.demo.serivcecomb210.module.TakeAwayRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.servicecomb.provider.pojo.RpcSchema;

/**
 * @author hezhangjian
 */
@Slf4j
@RpcSchema(schemaId = "takeaway")
public class TakeAwayImpl implements ITakeAway {

    @Override
    public void doRpc(RegionEnum regionEnum, TakeAwayRequest takeAwayRequest) {
        log.info("region enum is [{}], do rpc [{}]", regionEnum.getLocation(), takeAwayRequest.getName());
    }
}
