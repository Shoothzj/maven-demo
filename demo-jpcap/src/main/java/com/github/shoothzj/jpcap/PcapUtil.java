package com.github.shoothzj.jpcap;

import com.github.shoothzj.javatool.module.OperationSystem;
import com.github.shoothzj.javatool.util.OSUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @author hezhangjian
 */
@Slf4j
public class PcapUtil {

    public static final String TCP_TIMESTAMP = "tcp_timestamp";

    public static String getPcapDirectory() {
        final OperationSystem operationSystem = OSUtil.getOS();
        if (operationSystem.equals(OperationSystem.MAC)) {
            return "/Users/akka/OneDrive/抓包";
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
