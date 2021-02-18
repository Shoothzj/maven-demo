package com.github.shoothzj.demo.bookkeeper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class LogHeader {

    final int version;
    final long ledgersMapOffset;
    final int ledgersCount;

    LogHeader(int version, long ledgersMapOffset, int ledgersCount) {
        this.version = version;
        this.ledgersMapOffset = ledgersMapOffset;
        this.ledgersCount = ledgersCount;
    }

}
