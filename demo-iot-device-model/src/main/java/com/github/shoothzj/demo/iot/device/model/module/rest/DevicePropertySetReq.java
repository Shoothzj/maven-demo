package com.github.shoothzj.demo.iot.device.model.module.rest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;

/**
 * @author hezhangjian
 */
@Data
public class DevicePropertySetReq {

    private String deviceId;

    private ObjectNode properties;

    public DevicePropertySetReq() {
    }
}
