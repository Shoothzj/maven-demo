package com.github.shoothzj.demo.bookkeeper;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;

import java.nio.channels.FileChannel;

/**
 * @author hezhangjian
 */
@Slf4j
public class BookkeeperTool {

    public static final String ENTRY_LOG_PATH = "bk.entry-log.path";

    public static LogHeader readHeader(FileChannel fileChannel) throws Exception {
        final ByteBuf headers = Unpooled.buffer(Constant.HEADER_SIZE);
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
