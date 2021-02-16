package com.github.shoothzj.demo.trouble.shoot;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.github.shoothzj.demo.trouble.shoot.module.ReadType;
import com.github.shoothzj.demo.trouble.shoot.util.BkUtil;
import com.github.shoothzj.javatool.util.CommonUtil;
import com.github.shoothzj.javatool.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.bookkeeper.mledger.proto.MLDataFormats;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.pulsar.broker.service.schema.SchemaStorageFormat;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author hezhangjian
 */
@Slf4j
public class ZookeeperReader {

    @Parameter(names = "-read_type", description = "read_type")
    public ReadType readType;

    @Parameter(names = "-tenant", description = "tenant")
    public String tenant;

    @Parameter(names = "-namespace", description = "namespace")
    public String namespace;

    @Parameter(names = "-topic", description = "topic")
    public String topic;

    public void readManageLedger(byte[] bytes) throws Exception {
        MLDataFormats.ManagedLedgerInfo info = MLDataFormats.ManagedLedgerInfo.parseFrom(bytes);
        log.info("=======ml-info=======");
        log.info("manager ledger [{}]", info);
        log.info("=======ledgers=======");
        final List<MLDataFormats.ManagedLedgerInfo.LedgerInfo> ledgerInfoList = info.getLedgerInfoList();
        for (MLDataFormats.ManagedLedgerInfo.LedgerInfo ledgerInfo : ledgerInfoList) {
            log.info("ledger id [{}] entries [{}] size[{}] ts [{}]", ledgerInfo.getLedgerId(), ledgerInfo.getEntries(), ledgerInfo.getSize(), ledgerInfo.getTimestamp());
        }
        log.info("=======end=======");
    }

    public void readCursor(byte[] bytes) throws Exception {
        MLDataFormats.ManagedCursorInfo info = MLDataFormats.ManagedCursorInfo.parseFrom(bytes);
        log.info("=======cursor-info=======");
        log.info("cursor [{}]", info);
        log.info("=======end=======");
    }

    public void readSchema(byte[] bytes) throws Exception {
        final SchemaStorageFormat.SchemaLocator schemaLocator = SchemaStorageFormat.SchemaLocator.parseFrom(bytes);
        log.info("=======schemaLocator=======");
        log.info("schemaLocator [{}]", schemaLocator);
        log.info("=======end=======");
    }

    public void readLedger(long ledgerId, byte[] bytes) throws Exception {
        BkUtil.readLedger(ledgerId, bytes);
    }

    public static void main(String[] args) throws Exception {
        LogUtil.configureLog();
        final ZookeeperReader reader = new ZookeeperReader();
        JCommander.newBuilder().addObject(reader).build().parse(args);
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        final CuratorFramework framework = CuratorFrameworkFactory.newClient(System.getProperty("zookeeper.addr"), retryPolicy);
        framework.start();
        CommonUtil.sleep(TimeUnit.SECONDS, 3);
        final byte[] bytes = framework.getData().forPath(System.getProperty("zookeeper.path"));
        final String zookeeperType = System.getProperty("zookeeper.type");
        switch (zookeeperType) {
            case "SCHEMA":
                reader.readSchema(bytes);
                break;
            case "MANAGE_LEDGER":
                reader.readManageLedger(bytes);
                break;
            case "CURSOR":
                reader.readCursor(bytes);
                break;
            case "LEDGER":
                reader.readLedger(Long.parseLong(System.getProperty("ledger.id")), bytes);
                break;
            default:
                break;
        }
    }

}
