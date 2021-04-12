package com.github.shoothzj.demo.aliyun;

import com.github.shoothzj.javatool.util.EnvUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.util.LinkedHashMap;

/**
 * @author hezhangjian
 */
@Slf4j
public class AkSkReader {

    @SneakyThrows
    public static Ali getAli() {
        final Yaml yaml = new Yaml();
        final FileInputStream fileInputStream = new FileInputStream(EnvUtil.getUserHome() + "/.sh/ali.yaml");
        final LinkedHashMap<String, String> load = yaml.load(fileInputStream);
        log.info("load is [{}]", load);
        final Ali ali = new Ali();
        ali.setAk(load.get("ak"));
        ali.setSk(load.get("sk"));
        ali.setRegion("cn-hongkong");
        return ali;
    }

}
