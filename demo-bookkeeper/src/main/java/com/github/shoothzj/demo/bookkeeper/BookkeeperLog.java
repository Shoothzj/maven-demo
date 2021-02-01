package com.github.shoothzj.demo.bookkeeper;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.concurrent.FastThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.bookkeeper.bookie.BufferedReadChannel;
import org.apache.bookkeeper.bookie.EntryLogger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @author hezhangjian
 */
@Slf4j
public class BookkeeperLog {

    private final FastThreadLocal<ByteBuf> sizeBuffer = new FastThreadLocal<ByteBuf>() {
        @Override
        protected ByteBuf initialValue() throws Exception {
            // Max usage is size (4 bytes) + ledgerId (8 bytes) + entryid (8 bytes)
            return Unpooled.buffer(4 + 8 + 8);
        }
    };

    public void processLog(File file) throws Exception {
        final FileChannel fileChannel = new RandomAccessFile(file, "r").getChannel();
        final BufferedReadChannel bc = new BufferedReadChannel(fileChannel, 512);
        final PooledByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;
        ByteBuf headers = allocator.directBuffer(1024);
        bc.read(headers, 0);
        headers.readInt();
        final int headerVersion = headers.readInt();
        final long ledgersMapOffset = headers.readLong();
        final int ledgersCount1 = headers.readInt();
        log.info("header version {}", headerVersion);
        log.info("ledgers map offset {}", ledgersMapOffset);
        log.info("ledgers count {}", ledgersCount1);

        long offset = ledgersMapOffset;
        final int maxMapSize = 24 + 16 * 10000;
        ByteBuf ledgersMap = allocator.directBuffer(maxMapSize);

        try {
            while (offset < bc.size()) {
                // Read ledgers map size
                sizeBuffer.get().clear();
                bc.read(sizeBuffer.get(), offset);

                int ledgersMapSize = sizeBuffer.get().readInt();

                // Read the index into a buffer
                ledgersMap.clear();
                bc.read(ledgersMap, offset + 4, ledgersMapSize);

                // Discard ledgerId and entryId
                long lid = ledgersMap.readLong();
                if (lid != -1) {
                    throw new IOException("Cannot deserialize ledgers map from ledger " + lid);
                }

                long entryId = ledgersMap.readLong();
                if (entryId != -2) {
                    throw new IOException("Cannot deserialize ledgers map from entryId " + entryId);
                }

                // Read the number of ledgers in the current entry batch
                int ledgersCount = ledgersMap.readInt();

                // Extract all (ledger,size) tuples from buffer
                for (int i = 0; i < ledgersCount; i++) {
                    long ledgerId = ledgersMap.readLong();
                    long size = ledgersMap.readLong();

                    if (log.isDebugEnabled()) {
                        log.debug("Recovering ledgers maps for log {} -- Found ledger: {} with size: {}",
                                "$entryLogId", ledgerId, size);
                    }
//                    meta.addLedgerSize(ledgerId, size);
                }
                if (ledgersMap.isReadable()) {
                    throw new IOException("Invalid entry size when reading ledgers map");
                }

                // Move to next entry, if any
                offset += ledgersMapSize + 4;
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IOException(e);
        } finally {
            ledgersMap.release();
        }

    }

    public void scanLog(File file) throws Exception {
        final FileChannel fileChannel = new RandomAccessFile(file, "r").getChannel();
        final BufferedReadChannel bc = new BufferedReadChannel(fileChannel, 512);
        final PooledByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;
        ByteBuf headers = allocator.directBuffer(1024);
        bc.read(headers, 0);
        headers.readInt();
        final int headerVersion = headers.readInt();
        final long ledgersMapOffset = headers.readLong();
        final int ledgersCount1 = headers.readInt();
        log.info("header version {}", headerVersion);
        log.info("ledgers map offset {}", ledgersMapOffset);
        log.info("ledgers count {}", ledgersCount1);

        {
            try {
                long offset = ledgersMapOffset;
                final int maxMapSize = 24 + 16 * 10000;
                ByteBuf ledgersMap = allocator.directBuffer(maxMapSize);

                try {
                    while (offset < bc.size()) {
                        // Read ledgers map size
                        sizeBuffer.get().clear();
                        bc.read(sizeBuffer.get(), offset);

                        int ledgersMapSize = sizeBuffer.get().readInt();

                        // Read the index into a buffer
                        ledgersMap.clear();
                        bc.read(ledgersMap, offset + 4, ledgersMapSize);

                        // Discard ledgerId and entryId
                        long lid = ledgersMap.readLong();
                        if (lid != -1) {
                            throw new IOException("Cannot deserialize ledgers map from ledger " + lid);
                        }

                        long entryId = ledgersMap.readLong();
                        if (entryId != -2) {
                            throw new IOException("Cannot deserialize ledgers map from entryId " + entryId);
                        }

                        // Read the number of ledgers in the current entry batch
                        int ledgersCount = ledgersMap.readInt();

                        // Extract all (ledger,size) tuples from buffer
                        for (int i = 0; i < ledgersCount; i++) {
                            long ledgerId = ledgersMap.readLong();
                            long size = ledgersMap.readLong();

                            if (log.isDebugEnabled()) {
                                log.debug("Recovering ledgers maps for log {} -- Found ledger: {} with size: {}",
                                        "$entryLogId", ledgerId, size);
                            }
//                    meta.addLedgerSize(ledgerId, size);
                        }
                        if (ledgersMap.isReadable()) {
                            throw new IOException("Invalid entry size when reading ledgers map");
                        }

                        // Move to next entry, if any
                        offset += ledgersMapSize + 4;
                    }
                } catch (IndexOutOfBoundsException e) {
                    throw new IOException(e);
                } finally {
                    ledgersMap.release();
                }
            } catch (Exception e) {
                log.error("e");
            }
        }

        ByteBuf headerBuffer = Unpooled.buffer(4 + 8);
        // Start the read position in the current entry log file to be after
        // the header where all of the ledger entries are.
        long pos = 1024;

        // Start with a reasonably sized buffer size
        ByteBuf data = allocator.directBuffer(1024 * 1024);

        try {

            // Read through the entry log file and extract the ledger ID's.
            while (true) {
                // Check if we've finished reading the entry log file.
                if (pos >= bc.size()) {
                    break;
                }
                if (readFromLogChannel(1, bc, headerBuffer, pos) != headerBuffer.capacity()) {
                    log.warn("Short read for entry size from entrylog {}", "$entryLogId");
                    return;
                }
                long offset = pos;
                pos += 4;
                int entrySize = headerBuffer.readInt();
                long ledgerId = headerBuffer.readLong();
                headerBuffer.clear();

//                if (ledgerId == -1 || !scanner.accept(ledgerId)) {
//                    // skip this entry
//                    pos += entrySize;
//                    continue;
//                }

                if (ledgerId == -1 ) {
                    // skip this entry
                    pos += entrySize;
                    continue;
                }
                // read the entry

                data.clear();
                if (entrySize < 0) {
                    log.warn("bad read for ledger entry from entryLog {}@{} (entry size {})",
                            "$entryLogId", pos, entrySize);
                    return;
                }
                data.capacity(entrySize);
                if (entrySize > 500_000_000L) {
                    System.out.println(entrySize);
                }
                int rc = readFromLogChannel(1, bc, data, pos);
                if (rc != entrySize) {
                    log.warn("Short read for ledger entry from entryLog {}@{} ({} != {})",
                            "$entryLogId", pos, rc, entrySize);
                    return;
                }
                // process the entry
//              todo  scanner.process(ledgerId, offset, data);

                // Advance position to the next entry
                pos += entrySize;
            }
        } finally {
            data.release();
        }
    }


    /**
     * If the log id of current writable channel is the same as entryLogId and the position
     * we want to read might end up reading from a position in the write buffer of the
     * buffered channel, route this read to the current logChannel. Else,
     * read from the BufferedReadChannel that is provided.
     * @param entryLogId
     * @param channel
     * @param buff remaining() on this bytebuffer tells us the last position that we
     *             expect to read.
     * @param pos The starting position from where we want to read.
     * @return
     */
    private int readFromLogChannel(long entryLogId, BufferedReadChannel channel, ByteBuf buff, long pos)
            throws IOException {
//        EntryLogger.BufferedLogChannel bc = entryLogManager.getCurrentLogIfPresent(entryLogId);
//        if (null != bc) {
//            synchronized (bc) {
//                if (pos + buff.writableBytes() >= bc.getFileChannelPosition()) {
//                    return bc.read(buff, pos);
//                }
//            }
//        }
        return channel.read(buff, pos);
    }

}
