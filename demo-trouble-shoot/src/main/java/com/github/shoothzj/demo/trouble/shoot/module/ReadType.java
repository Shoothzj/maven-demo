package com.github.shoothzj.demo.trouble.shoot.module;

/**
 * @author hezhangjian
 */
public enum ReadType {

    /**
     * /schemas/tenant/namespace/topic
     */
    SCHEMA,
    /**
     * /managed-ledgers/tenant/namespace/persistent/topic
     */
    MANAGE_LEDGER,
    /**
     * /managed-ledgers/tenant/namespace/persistent/topic/consumerId
     */
    CURSOR;

}
