package com.github.shoothzj.demo.serivcecomb210.rpc;

import com.github.shoothzj.demo.serivcecomb210.module.RegionEnum;
import com.github.shoothzj.demo.serivcecomb210.module.TakeAwayRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author hezhangjian
 */
public interface ITakeAway {

    void doRpc(RegionEnum regionEnum, @Valid @NotNull TakeAwayRequest takeAwayRequest);

}
