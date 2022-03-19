package com.starry.codeview.ioLearn.niosockets;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class NIOClient {

    public static void loginSocket(String msg) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 7001));
        socketChannel.configureBlocking(false);

        ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
        socketChannel.write(byteBuffer);
        System.in.read();
        socketChannel.close();
    }

    public static void main(String[] args) throws Exception {
        loginSocket("你好呀，我是客户端。");

    }
}
