package com.github.shoothzj.jpcap.ex;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class PulsarDecodeException extends IllegalArgumentException {

    public PulsarDecodeException() {
    }

    public PulsarDecodeException(String s) {
        super(s);
    }

    public PulsarDecodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PulsarDecodeException(Throwable cause) {
        super(cause);
    }

}
