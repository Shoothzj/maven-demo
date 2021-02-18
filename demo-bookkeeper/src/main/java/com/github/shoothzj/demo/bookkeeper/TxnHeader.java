package com.github.shoothzj.demo.bookkeeper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class TxnHeader {

    final int version;

    public TxnHeader(int version) {
        this.version = version;
    }

}
