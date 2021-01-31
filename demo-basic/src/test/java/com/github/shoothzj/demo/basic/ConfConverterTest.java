package com.github.shoothzj.demo.basic;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfConverterTest {

    @Test
    public void test() throws Exception {
        final ConfConverter confConverter = new ConfConverter("/Users/akka/master/maven-demo/demo-basic/src/main/resources/bk_server.conf", "/Users/akka/master/maven-demo/demo-basic/src/main/resources/bk_server2.conf");
        confConverter.init();
        Map<String, String> map = new HashMap<>();
        map.put("zkEnableSecurity", "true");
        confConverter.modify(map);
        confConverter.end();
    }

}