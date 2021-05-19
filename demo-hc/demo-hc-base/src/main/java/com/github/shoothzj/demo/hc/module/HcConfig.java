package com.github.shoothzj.demo.hc.module;

import lombok.Data;

/**
 * @author hezhangjian
 */
@Data
public class HcConfig {

    private String region;

    private String ak;

    private String sk;

    private String projectId;

    public HcConfig() {
    }
}
