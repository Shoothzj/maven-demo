package com.github.shoothzj.demo.iot.mdevice.manage.controller;

import com.github.shoothzj.demo.iot.mdevice.manage.domain.RegisterDevicePO;
import com.github.shoothzj.demo.iot.mdevice.manage.module.rest.RegisterDeviceReq;
import com.github.shoothzj.demo.iot.mdevice.manage.repository.RegisterDeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
public class DeviceController {

    @Autowired
    private RegisterDeviceRepository registerDeviceRepository;

    /**
     * https://help.aliyun.com/document_detail/69470.html?spm=5176.12901015.0.i12901015.29f8525cwfzaTD
     */
    @PostMapping(path = "/device-manage/register-device")
    public void registerDevice(@RequestBody RegisterDeviceReq registerDeviceReq) {
        RegisterDevicePO registerDevicePO = new RegisterDevicePO();
        registerDevicePO.setDeviceName(UUID.randomUUID().toString());
        registerDevicePO.setDevEui(registerDeviceReq.getDevEui());
        registerDevicePO.setPinCode(registerDeviceReq.getPinCode());
        registerDevicePO.setNickName(registerDeviceReq.getNickName());
        registerDeviceRepository.save(registerDevicePO);
    }


}
