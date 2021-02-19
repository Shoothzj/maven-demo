package com.github.shoothzj.demo.nio;

import com.github.shoothzj.javatool.util.StreamUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * @author hezhangjian
 */
@Slf4j
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel channel;

    public ReadCompletionHandler(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        final byte[] body = new byte[attachment.remaining()];
        attachment.get(body);
        final String req = new String(body, StandardCharsets.UTF_8);
        log.info("the time server receive order [{}]", body);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(req) ? LocalDateTime.now().toString() : "BAD ORDER";
        doWrite(currentTime);
    }

    private void doWrite(String currentTime) {
        if (currentTime == null || currentTime.trim().length() == 0) {
            return;
        }
        byte[] bytes = currentTime.getBytes(StandardCharsets.UTF_8);
        final ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if (attachment.hasRemaining()) {
                    channel.write(attachment, attachment, this);
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                StreamUtil.close(channel);
            }
        });
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        StreamUtil.close(channel);
    }

}
