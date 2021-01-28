package com.github.shoothzj.demo.grammar;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author hezhangjian
 */
@Slf4j
public class StringUtilTest {

    public static void main(String[] args) {
        char space = ' ';
        final String join = String.join(" ", "a", "b");
        System.out.println(join);
    }

}
