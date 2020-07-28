package com.github.shoothzj.demo.serivcecomb210.module;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public enum RegionEnum {

    CN("CHINA"),
    US("USA");

    private String location;

    RegionEnum(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
