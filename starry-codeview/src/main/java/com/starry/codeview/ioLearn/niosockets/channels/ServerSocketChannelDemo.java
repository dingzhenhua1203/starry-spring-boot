package com.starry.codeview.ioLearn.niosockets.channels;

import com.starry.starrycore.StreamUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ServerSocketChannelDemo {
    public static void main(String[] args) {
        Socket socket = null;
        SocketChannel socketChannel = null;
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()
                    .bind(new InetSocketAddress(6501));

            // ServerSocket serverSocket = serverSocketChannel.socket();
            // socket = serverSocket.accept();
            socketChannel = serverSocketChannel.accept();
            ByteBuffer[] byteBuffers = new ByteBuffer[2];
            byteBuffers[0] = ByteBuffer.allocate(5);
            byteBuffers[1] = ByteBuffer.allocate(6);
            /*
            这是bytes数组获取，也可以用byteBuffers数组分段获取
            byte[] bytes = new byte[1024];
            while (socket.getInputStream().read(bytes, 0, 10) != -1) {
                System.out.println(StreamUtils.bytesToStr(bytes, 10));
            }
            */
            while (socketChannel.read(byteBuffers) != -1) {
                Arrays.stream(byteBuffers).forEach(x -> {
                    x.flip();
                    System.out.println(StreamUtils.bytesToStr(x.array()));
                    x.clear();
                });

            }
        } catch (Exception e) {

        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
