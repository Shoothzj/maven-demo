package com.github.shoothzj.demo.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

/**
 * @author hezhangjian
 */
@Slf4j
public class Test {

    public static void main(String[] args) throws Exception{
        final ObjectMapper objectMapper = new ObjectMapper();
        String str = "{\"inDto\":{\"in\":\"{\\\"a\\\":{\\\"c\\\":\\\"d\\\"}}\"}}";
        System.out.println(str);
        final OutDto jacksonDto = objectMapper.readValue(str, OutDto.class);
        System.out.println(jacksonDto);
        final JSONObject jsonObject = JSONObject.fromObject(str);
        System.out.println(jsonObject);
        final Object jsonLibDto = JSONObject.toBean(jsonObject, OutDto.class);
        System.out.println(jsonLibDto);
    }

}
