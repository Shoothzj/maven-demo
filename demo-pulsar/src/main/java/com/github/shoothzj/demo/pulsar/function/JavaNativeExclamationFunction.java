package com.github.shoothzj.demo.pulsar.function;

import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;

/**
 * @author hezhangjian
 */
@Slf4j
public class JavaNativeExclamationFunction implements Function<String, String> {

    @Override
    public String process(String input, Context context) throws Exception {
        return String.format("%s!", input);
    }

}
