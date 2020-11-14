package com.github.shoothzj.demo.netty.echo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class EchoConstant {

    public static final boolean ECHO_SSL = System.getProperty("ssl") != null;

    public static final String ECHO_HOST = System.getProperty("host", "127.0.0.1");

    public static final int ECHO_PORT = Integer.parseInt(System.getProperty("port", "8007"));

    public static final int ECHO_SIZE = Integer.parseInt(System.getProperty("size", "256"));

}