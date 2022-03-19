package com.starry.codeview.ioLearn.niosockets;

import com.starry.starrycore.StreamUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    public static void serverStart() throws Exception {
        // 1.创建服务端监听channel,并配置为非阻塞
        ServerSocketChannel ss = ServerSocketChannel.open()
                .bind(new InetSocketAddress(7001));
        ss.configureBlocking(false);

        // 2.创建选择器并把通道注册到Selector上，并注册感兴趣的事件
        Selector selector = Selector.open();
        SelectionKey selectionKey = ss.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("没有用户连接.....");
                continue;
            }

            Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
            while (selectionKeyIterator.hasNext()) {
                SelectionKey key = selectionKeyIterator.next();
                // 取消选择键
                selectionKeyIterator.remove();

                if (key.isConnectable()) {
                    System.out.println("isConnectable=true：" + key.hashCode());
                    continue;
                }
                // 处理 accept 就绪事件
                if (key.isAcceptable()) {
                    // 这里表示有新的客户端连接
                    System.out.println("accept事件,有客户端接入：" + key.hashCode());
                    SocketChannel channel = ss.accept();
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);
                }
                if (key.isReadable()) {
                    System.out.println("来自客户端" + key.hashCode() + ",发送了信息：");
                    SocketChannel channel = (SocketChannel) key.channel();
                    channel.configureBlocking(false);
                    ByteBuffer byteBuffer = ByteBuffer.allocate(256);
                    int length = -1;
                    while ((length = channel.read(byteBuffer)) > 0) {
                        byteBuffer.flip();
                        System.out.println(StreamUtils.bytesToStr(byteBuffer.array(), byteBuffer.limit()));
                        byteBuffer.clear();
                    }
                    if (length <= 0) {
                        // 否则客户端关闭就会导致死循环 打印“来自客户端94438417,发送了信息：”
                        System.out.println("客户端" + key.hashCode() + "关闭");
                        key.cancel();
                    }

                }
            }
        }

    }


    public static void main(String[] args) throws Exception {
        serverStart();
    }
}
