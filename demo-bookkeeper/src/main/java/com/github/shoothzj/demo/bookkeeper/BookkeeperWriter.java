package com.github.shoothzj.demo.bookkeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.bookkeeper.client.BookKeeper;
import org.apache.bookkeeper.client.LedgerHandle;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author hezhangjian
 */
@Slf4j
public class BookkeeperWriter {


    private static long ledgerId;

    private static byte[] ledgerPassword = "".getBytes(StandardCharsets.UTF_8);

    public static void main(String[] args) throws Exception {
        BookKeeper bkc = new BookKeeper("localhost:2181");
        LedgerHandle lh = bkc.createLedger(1, 1, 1, BookKeeper.DigestType.CRC32, ledgerPassword);
        ledgerId = lh.getId();
        ByteBuffer entry = ByteBuffer.allocate(4);

        for (int i = 0; i < 1; i++) {
            entry.putInt(i);
            entry.position(0);
            lh.addEntry(entry.array());
        }
        lh.close();
        lh = bkc.openLedger(ledgerId, BookKeeper.DigestType.CRC32, ledgerPassword);
        lh.close();
        bkc.close();
    }

}
