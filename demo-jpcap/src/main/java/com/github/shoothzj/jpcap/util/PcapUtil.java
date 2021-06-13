package com.github.shoothzj.jpcap.util;

import com.github.shoothzj.javatool.module.OperationSystem;
import com.github.shoothzj.javatool.util.OsUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @author hezhangjian
 */
@Slf4j
public class PcapUtil {

    public static String getPcapDirectory() {
        final OperationSystem operationSystem = OsUtil.getOs();
        if (operationSystem.equals(OperationSystem.MAC)) {
            return "/Users/akka/OneDrive/pcap_capture";
        } else if (operationSystem.equals(OperationSystem.WINDOWS)) {
            return "D:\\OneDrive\\抓包\\pcap_capture";
        }
        return "";
    }

    public static String getPcapPath(String... pcapName) {
        final StringBuilder sb = new StringBuilder(getPcapDirectory());
        for (String s : pcapName) {
            sb.append(File.separator).append(s);
        }
        return sb.toString();
    }

}
