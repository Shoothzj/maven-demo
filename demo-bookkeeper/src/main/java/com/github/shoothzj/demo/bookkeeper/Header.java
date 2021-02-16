package com.github.shoothzj.demo.bookkeeper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class Header {

    final int version;
    final long ledgersMapOffset;
    final int ledgersCount;

    Header(int version, long ledgersMapOffset, int ledgersCount) {
        this.version = version;
        this.ledgersMapOffset = ledgersMapOffset;
        this.ledgersCount = ledgersCount;
    }

}
