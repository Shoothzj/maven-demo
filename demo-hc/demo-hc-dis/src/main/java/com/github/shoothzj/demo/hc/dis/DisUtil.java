package com.github.shoothzj.demo.hc.dis;

import com.github.shoothzj.demo.hc.HcConfigReader;
import com.github.shoothzj.demo.hc.module.HcConfig;
import com.huaweicloud.dis.DIS;
import com.huaweicloud.dis.DISAsync;
import com.huaweicloud.dis.DISClientAsync;
import com.huaweicloud.dis.DISClientBuilder;
import com.huaweicloud.dis.DISConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class DisUtil {

    private static final HcConfig HC_CONFIG = HcConfigReader.getHcConfig();

    public static DIS getDis() {
        final DIS dis = DISClientBuilder.standard()
                .withEndpoint(DisConstant.ENDPOINT_CN_NORTH4)
                .withAk(HC_CONFIG.getAk())
                .withSk(HC_CONFIG.getSk())
                .withProjectId(HC_CONFIG.getProjectId())
                .withRegion(HC_CONFIG.getRegion())
                .build();
        return dis;
    }

    public static DISAsync getDisAsync() {
        final DISConfig disConfig = new DISConfig();
        disConfig.setEndpoint(DisConstant.ENDPOINT_CN_NORTH4);
        disConfig.setAK(HC_CONFIG.getAk());
        disConfig.setSK(HC_CONFIG.getSk());
        disConfig.setProjectId(HC_CONFIG.getProjectId());
        disConfig.setRegion(HC_CONFIG.getRegion());
        return new DISClientAsync(disConfig);
    }

}
