package com.github.shoothzj.demo.bookkeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.bookkeeper.client.BookKeeper;
import org.apache.bookkeeper.client.LedgerEntry;
import org.apache.bookkeeper.client.LedgerHandle;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author hezhangjian
 */
@Slf4j
public class BookKeeperExample {

    private static long ledgerId;

    private static byte[] ledgerPassword = "".getBytes(StandardCharsets.UTF_8);

    private static List<byte[]> entries = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        BookKeeper bkc = new BookKeeper("localhost:2181");
        LedgerHandle lh = bkc.createLedger(1, 1, 1, BookKeeper.DigestType.CRC32, ledgerPassword);
        ledgerId = lh.getId();
        ByteBuffer entry = ByteBuffer.allocate(4);

        for (int i = 0; i < 10; i++) {
            entry.putInt(i);
            entry.position(0);
            entries.add(entry.array());
            lh.addEntry(entry.array());
        }
        lh.close();
        lh = bkc.openLedger(ledgerId, BookKeeper.DigestType.CRC32, ledgerPassword);

        Enumeration<LedgerEntry> ls = lh.readEntries(0, 9);
        int i = 0;
        while (ls.hasMoreElements()) {
            ByteBuffer origbb = ByteBuffer.wrap(entries.get(i++));
            Integer origEntry = origbb.getInt();
            ByteBuffer result = ByteBuffer.wrap(ls.nextElement().getEntry());
            Integer retrEntry = result.getInt();
        }
        lh.close();
        bkc.close();
    }

}
