package com.github.shoothzj.demo.jackson;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

/**
 * @author hezhangjian
 */
@Slf4j
public class TestJsonLib {

    public static void main(String[] args) {
        final OutDto value = new OutDto();
        final InDto inDto = new InDto();
        inDto.setIn("{\"a\":{\"c\":\"d\"}}");
        value.setInDto(inDto);
        final String bean2Json = bean2Json(value);
        System.out.println(bean2Json);
        final OutDto outDto = json2Bean(bean2Json, OutDto.class);
        System.out.println(outDto);
    }

    public static String bean2Json(Object obj) {
        JSONObject jsonObject = JSONObject.fromObject(obj);
        return jsonObject.toString();
    }

    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
        return (T) JSONObject.toBean(JSONObject.fromObject(jsonStr), objClass);
    }

}
