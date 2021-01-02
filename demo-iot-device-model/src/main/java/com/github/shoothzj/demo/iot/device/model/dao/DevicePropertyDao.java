package com.github.shoothzj.demo.iot.device.model.dao;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

/**
 * @author hezhangjian
 * https://help.aliyun.com/document_detail/69579.html?spm=a2c4g.11186623.6.873.1640f918jLz87Q
 */
@Data
@Document(collection = "device_property")
public class DevicePropertyDao {

    @Id
    @Field("device_id")
    private String deviceId;

    @Field("properties")
    private ObjectNode properties;

}
