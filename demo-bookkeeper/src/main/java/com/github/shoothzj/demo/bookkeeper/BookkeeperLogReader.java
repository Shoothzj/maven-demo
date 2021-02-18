package com.github.shoothzj.demo.bookkeeper;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @author hezhangjian
 */
@Slf4j
public class BookkeeperLogReader {

    private static final int HEADER_SIZE = 1024;

    public void read(String fileName) throws Exception {
        final RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "r");
        final FileChannel fileChannel = randomAccessFile.getChannel();

        final LogHeader header = readHeader(fileChannel);
        //ledger map 放在文件的最后，读取
        long offset = header.ledgersMapOffset;

        int ledgersMapSize = ledgerMapSize(fileChannel, offset);
        {
            final ByteBuf ledgerMapBuf = Unpooled.buffer(ledgersMapSize);
            final int readAux = fileChannel.read(ledgerMapBuf.internalNioBuffer(0, ledgersMapSize), offset + 4);
            ledgerMapBuf.writerIndex(readAux);
            System.out.println(readAux);

            // Discard ledgerId and entryId
            long lid = ledgerMapBuf.readLong();
            if (lid != -1) {
                throw new IOException("Cannot deserialize ledgers map from ledger " + lid);
            }

            long entryId = ledgerMapBuf.readLong();
            if (entryId != -2) {
                throw new IOException("Cannot deserialize ledgers map from entryId " + entryId);
            }

            // Read the number of ledgers in the current entry batch
            int ledgersCount = ledgerMapBuf.readInt();

            for (int i = 0; i < ledgersCount; i++) {
                final long ledgerId = ledgerMapBuf.readLong();
                final long size = ledgerMapBuf.readLong();
                log.info("ledgerId [{}] size [{}]", ledgerId, size);
            }
        }

        //扫描entry
        {
            fileChannel.position(HEADER_SIZE);
            //read ledgers map size
            final int size = (int) fileChannel.size();
            final int excludeHeaderCapacity = size - HEADER_SIZE;
            final int bodyCapacity = (int) (excludeHeaderCapacity - (size - offset));
            final ByteBuf bodyBuf = Unpooled.buffer(bodyCapacity);
            final int read = fileChannel.read(bodyBuf.internalNioBuffer(0, bodyCapacity), HEADER_SIZE);
            bodyBuf.writerIndex(read);
            while (bodyBuf.readableBytes() != 0) {
                final int entrySize = bodyBuf.readInt();
                final long ledgerId = bodyBuf.readLong();
                final long entryId = bodyBuf.readLong();
                log.debug("ledgerId [{}] entryId [{}]", ledgerId, entryId);
                final byte[] bodyBytes = new byte[entrySize - 16];
                bodyBuf.readBytes(bodyBytes);
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

    private LogHeader readHeader(FileChannel fileChannel) throws Exception {
        final ByteBuf headers = Unpooled.buffer(1024);
        final int read = fileChannel.read(headers.internalNioBuffer(0, 1024));
        headers.writerIndex(read);
        final byte[] bkloByte = new byte[4];
        headers.readBytes(bkloByte, 0, 4);
        final String bklo = new String(bkloByte);
        final int headerVersion = headers.readInt();
        log.info("[{}] header version is [{}]", bklo, headerVersion);
        final long ledgersMapOffset = headers.readLong();
        int ledgersCount = headers.readInt();
        log.info("map offset is [{}]", ledgersMapOffset);
        log.info("ledgers count is [{}]", ledgersCount);
        return new LogHeader(headerVersion, ledgersMapOffset, ledgersCount);
    }

}
