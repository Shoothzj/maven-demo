package com.github.shoothzj.demo.iot.mdevice.manage.module.rest;

import lombok.Data;

@Data
public class RegisterDeviceReq {

    /**
     * 为待注册的设备命名。设备名称长度为4-32个字符，
     * 可以包含英文字母、数字和特殊字符：
     * 短划线（-）、下划线（_）、at符号（@）、点号（.）、和英文冒号（:）。
     *
     * DeviceName通常与ProductKey组合使用，用作设备标识。
     */
    private String deviceName;

    /**
     * LoRaWAN设备的DevEUI。
     *
     * 创建LoRaWAN设备时，该参数必传。
     */
    private String devEui;

    /**
     * LoRaWAN设备的PIN Code，用于校验DevEUI的合法性。
     *
     * 创建LoRaWAN设备时，该参数必传。
     */
    private String pinCode;

    /**
     * 为待注册的设备设置备注名称。备注名称长度为4-64个字符，可包含中文汉字、英文字母、数字和下划线（_）。一个中文汉字算2字符。
     */
    private String nickName;

    public RegisterDeviceReq() {
    }
}
