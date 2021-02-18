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
public class BookkeeperLogForceReader {

    private static final int HEADER_SIZE = 1024;

    public void read(String fileName) throws Exception {
        final RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "r");
        final FileChannel fileChannel = randomAccessFile.getChannel();

        final LogHeader header = BookkeeperTool.readHeader(fileChannel);

//        扫描entry
        {
            try {
                fileChannel.position(HEADER_SIZE);
                //read ledgers map size
                final int size = (int) fileChannel.size();
                final int restCapacity = size - HEADER_SIZE;
                final ByteBuf bodyBuf = Unpooled.buffer(restCapacity);
                final int read = fileChannel.read(bodyBuf.internalNioBuffer(0, restCapacity), HEADER_SIZE);
                bodyBuf.writerIndex(read);
                while (bodyBuf.readableBytes() != 0) {
                    final int entrySize = bodyBuf.readInt();
                    final long ledgerId = bodyBuf.readLong();
                    final long entryId = bodyBuf.readLong();
                    log.info("entrySize [{}] ledgerId [{}] entryId [{}]", entrySize, ledgerId, entryId);
                    bodyBuf.readBytes(new byte[entrySize - 16]);
                }
            } catch (Exception e) {
                log.error("do everything you want ", e);
            }
        }
    }


}
