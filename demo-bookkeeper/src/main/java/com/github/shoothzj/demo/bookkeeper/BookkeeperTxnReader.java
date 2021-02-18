package com.github.shoothzj.demo.bookkeeper;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @author hezhangjian
 */
@Slf4j
public class BookkeeperTxnReader {

    private static final int HEADER_SIZE = 512;

    public void read(String fileName) throws Exception {
        final RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "r");
        final FileChannel fileChannel = randomAccessFile.getChannel();

        final TxnHeader header = readHeader(fileChannel);

        //扫描entry
        {
            fileChannel.position(HEADER_SIZE);
            final int fileSize = (int) fileChannel.size();
            final int excludeHeaderCapacity = fileSize - HEADER_SIZE;
            final ByteBuf bodyBuf = Unpooled.buffer(excludeHeaderCapacity);
            final int read = fileChannel.read(bodyBuf.internalNioBuffer(0, excludeHeaderCapacity), HEADER_SIZE);
            bodyBuf.writerIndex(read);
            while (bodyBuf.readableBytes() != 0) {
                final int size = bodyBuf.readInt();
                if (size > 0) {
                    //read the ledger id and entry id
                    final long ledgerId = bodyBuf.readLong();
                    final long entryId = bodyBuf.readLong();
                    if (entryId < 0) {
                        if (entryId == -4096) {
                            int masterKeySize = bodyBuf.readInt();
                            final byte[] bytes = new byte[masterKeySize];
                            bodyBuf.readBytes(bytes);
                        } else if (entryId == -8192) {
                            log.info("ledger id is [{}] entry Id is [{}]", ledgerId, entryId);
                        } else {
                            log.info("entry Id is [{}]", entryId);
                        }
                        continue;
                    }
                    final long lac = bodyBuf.readLong();
                    log.info("ledger id is [{}] entry id is [{}] lac is [{}] size is [{}]", ledgerId, entryId, lac, size);
                    final byte[] bytes = new byte[size - 24];
                    bodyBuf.readBytes(bytes);
                } else if (size == 0) {
                    continue;
                } else if (size == -256) {
                    final int readInt = bodyBuf.readInt();
                    final byte[] bytes = new byte[readInt];
                    bodyBuf.readBytes(bytes);
                } else {
                    throw new IllegalStateException("why here?");
                }
            }
        }
    }

    private int ledgerMapSize(FileChannel fileChannel, long offset) throws Exception {
        //read ledgers map size
        final ByteBuf sizeBuf = Unpooled.buffer(4);
        final int read = fileChannel.read(sizeBuf.internalNioBuffer(0, 4), offset);
        sizeBuf.writerIndex(read);
        System.out.println(read);
        int ledgersMapSize = sizeBuf.readInt();
        log.debug("map size {}", ledgersMapSize);
        return ledgersMapSize;
    }

    private TxnHeader readHeader(FileChannel fileChannel) throws Exception {
        final ByteBuf headers = Unpooled.buffer(HEADER_SIZE);
        final int read = fileChannel.read(headers.internalNioBuffer(0, HEADER_SIZE));
        headers.writerIndex(read);
        final byte[] bklgByte = new byte[4];
        headers.readBytes(bklgByte, 0, 4);
        final int headerVersion = headers.readInt();
        return new TxnHeader(headerVersion);
    }


}
