package com.github.shoothzj.demo.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class TestJackson {

    public static void main(String[] args) throws Exception {

        final ObjectMapper objectMapper = new ObjectMapper();
        final OutDto value = new OutDto();
        final InDto inDto = new InDto();
        inDto.setIn("{\"a\":{\"c\":\"d\"}}");
        value.setInDto(inDto);
        // {"a":"b"}
        final String valueAsString = objectMapper.writeValueAsString(value);
        System.out.println(valueAsString);
        final OutDto outDto = objectMapper.readValue(valueAsString, OutDto.class);
        System.out.println(outDto);
    }

}
