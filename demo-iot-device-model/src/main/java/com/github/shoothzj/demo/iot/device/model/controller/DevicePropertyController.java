package com.github.shoothzj.demo.iot.device.model.controller;

import com.github.shoothzj.demo.iot.device.model.dao.DevicePropertyDao;
import com.github.shoothzj.demo.iot.device.model.module.rest.DevicePropertySetReq;
import com.github.shoothzj.demo.iot.device.model.repo.DevicePropertyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hezhangjian
 */
@Slf4j
@RestController
public class DevicePropertyController {

    @Autowired
    private DevicePropertyRepository devicePropertyRepository;

    /**
     * https://help.aliyun.com/document_detail/69579.html?spm=a2c4g.11186623.6.873.1640f918jLz87Q
     */
    @PostMapping(path = "/device-manage/register-device")
    public void setDeviceProperty(@RequestBody DevicePropertySetReq devicePropertySetReq) {
        final DevicePropertyDao devicePropertyDao = new DevicePropertyDao();
        devicePropertyDao.setDeviceId(devicePropertySetReq.getDeviceId());
        devicePropertyDao.setProperties(devicePropertyDao.getProperties());
        devicePropertyRepository.save(devicePropertyDao);
    }


}
