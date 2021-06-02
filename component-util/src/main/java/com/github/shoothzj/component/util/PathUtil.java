package com.github.shoothzj.component.util;

import com.google.common.io.Resources;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;

/**
 * @author hezhangjian
 */
@Slf4j
public class PathUtil {

    public static String getResourcesPath() {
        final URL data = Resources.getResource("");
        return data.getPath();
    }

}
