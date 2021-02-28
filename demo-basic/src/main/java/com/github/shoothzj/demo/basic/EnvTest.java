package com.github.shoothzj.demo.basic;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class EnvTest {

    public static void main(String[] args) {
        final String property = System.getProperty("user.home");
        System.out.println(property);
        final String unExistVariable = System.getenv("UN_EXIST_VARIABLE");
        System.out.println(unExistVariable);
    }

}
