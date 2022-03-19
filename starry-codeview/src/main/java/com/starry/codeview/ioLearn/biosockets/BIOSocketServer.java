package com.starry.codeview.ioLearn.biosockets;

import com.starry.codeview.jucstudy.ThreadPoolUtils;
import com.starry.starrycore.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 原生BIO的ServerSocket，线程是阻塞在那等待客户端输入信息的
 * 服务端解释
 * 1.通过ServerSocket开启一个端口上的服务
 * 2.主线程开始监听等待连接
 * 3.每有一个客户端连接就开启子线程来建立连接，处理消息
 * 4.当客户端关闭的时候，服务端的子线程才会进入finally执行close动作关闭socket连接
 * 客户端这里可以用
 * 1.telnet ip port 来模拟,
 * 2.ctrl +]来进入输入状态，
 * 3.然后send xxx就可以发送信息了

 */
public class BIOSocketServer {

    public static void receiveSocket(Socket socket) {
        try {
            // 从socket中获取输入流
            System.out.println(Thread.currentThread().getName() + "...与客户端通信，从socket中获取输入流");
            InputStream inputStream = socket.getInputStream();

            byte[] bytes = new byte[1024];
            int read = 0;
            while ((read = inputStream.read(bytes)) != -1) {
                System.out.println(Thread.currentThread().getName() + "...读取到数据:" + StreamUtils.bytesToStr(bytes, read));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接
                System.out.println(Thread.currentThread().getName() + "...关闭socket连接");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        // 开一个socket服务端口
        ServerSocket ss = new ServerSocket(6500);
        System.out.println("soctek服务启动，端口号6500");
        // 监听客户端
        while (true) {
            // 和客户端建立连接
            Socket accept = ss.accept();
            System.out.println(Thread.currentThread().getName() + "...连接上一个客户端了");
            ThreadPoolUtils.threadPools.execute(() -> {
                // 与客户端通信
                receiveSocket(accept);
            });
        }

    }


}
