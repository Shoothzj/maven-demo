package com.github.shoothzj.demo.mybatis.domain;

import lombok.Data;

/**
 * @author shoothzj
 */
@Data
public class ConfigPo {

    private String configName;

    private String configSchema;

    private int version;

    public ConfigPo() {
    }

}
