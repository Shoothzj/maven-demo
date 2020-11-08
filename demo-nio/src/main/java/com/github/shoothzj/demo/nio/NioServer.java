package com.github.shoothzj.demo.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author hezhangjian
 */
@Slf4j
public class NioServer {

    public static void main(String[] args) throws IOException {
        final ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.socket().bind(new InetSocketAddress(9998));
        serverChannel.configureBlocking(false);

        //创建Selector
        final Selector selector = Selector.open();

        //注册
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            //Select 可能在无就绪事件时返回
            selector.select();
            final Set<SelectionKey> readyKeys = selector.selectedKeys();
            final Iterator<SelectionKey> it = readyKeys.iterator();
            while (it.hasNext()) {
                final SelectionKey selectionKey = it.next();
                // process
                it.remove();
            }
        }
    }

}
