package com.github.shoothzj.demo.iotda;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Huawei Cloud IOTDA
 */
@Slf4j
public class Sha256Mac {

    /***
     * 调用sha256算法进行哈希
     *
     * @param message
     * @param tStamp
     * @return
     */
    public static String sha256Mac(String message, String tStamp) {
        String passWord = null;
        try {
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(tStamp.getBytes(), "HmacSHA256");
            sha256HMAC.init(secretKey);
            byte[] bytes = sha256HMAC.doFinal(message.getBytes());
            passWord = byteArrayToHexString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return passWord;
    }

    /***
     * byte数组转16进制字符串
     *
     * @param b
     * @return
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs.append('0');
            }
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }


}
