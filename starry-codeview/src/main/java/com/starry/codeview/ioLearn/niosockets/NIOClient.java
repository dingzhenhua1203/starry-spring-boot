package com.starry.codeview.ioLearn.niosockets;

import com.starry.starrycore.StreamUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class NIOClient {
    private SocketChannel socketChannel = null;

    public void loginSocket() throws Exception {
        socketChannel = SocketChannel.open();
        boolean connectFlag = socketChannel.connect(new InetSocketAddress("127.0.0.1", 7001));
        socketChannel.configureBlocking(false);
        if (!connectFlag) {
            socketChannel.finishConnect();
            System.out.println("连接服务器失败，自动断开.....");
            return;
        }
    }

    public void sendMsg(String msg) {
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
            socketChannel.write(byteBuffer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void receiveMsg() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(256);
        try {
            while (socketChannel.read(byteBuffer) > 0) {
                System.out.println(StreamUtils.bytesToStr(byteBuffer.array(), byteBuffer.limit()));
                byteBuffer.clear();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void closeChannel() throws IOException {
        if (socketChannel != null)
            socketChannel.close();
    }

    public static void main(String[] args) throws Exception {
        NIOClient client = new NIOClient();
        client.loginSocket();
        new Thread(() -> {
            client.receiveMsg();
        }).start();
        // 从键盘接收数据
        Scanner scan = new Scanner(System.in);
        // 判断是否还有输入
        if (scan.hasNextLine()) {
            String str = scan.nextLine();
            client.sendMsg(str);
        }
        // scan.close();
    }
}
