package com.starry.codeview.ioLearn.niosockets;

import com.starry.starrycore.StreamUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NIOServer {

    private ServerSocketChannel serverSocketChannel = null;
    private Selector selector = null;
    private final Map<String, SocketChannel> connectedClientMap = new HashMap<>();

    public void serverStart() throws Exception {
        // 1.创建服务端监听channel,并配置为非阻塞
        serverSocketChannel = ServerSocketChannel.open()
                .bind(new InetSocketAddress(7001));
        serverSocketChannel.configureBlocking(false);

        // 2.创建选择器并把通道注册到Selector上，并注册感兴趣的事件
        selector = Selector.open();
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    /**
     * 轮询监听客户端事件
     */
    public void serverPollingListen() {
        while (true) {
            try {
                if (selector.select(1000) == 0) {
                    // System.out.println("等待用户连接.....");
                    continue;
                }

                Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
                while (selectionKeyIterator.hasNext()) {
                    SelectionKey key = selectionKeyIterator.next();
                    // 取消选择键
                    selectionKeyIterator.remove();

                    // 处理 accept 就绪事件，有有新的客户端连接就会触发
                    if (key.isAcceptable()) {
                        // 获取和客户端之间的通道
                        SocketChannel clientSocketChannel = serverSocketChannel.accept();
                        clientSocketChannel.configureBlocking(false);
                        clientSocketChannel.register(selector, SelectionKey.OP_READ);
                        connectedClientMap.put(clientSocketChannel.getRemoteAddress().toString(), clientSocketChannel);
                        System.out.println("客户端" + clientSocketChannel.getRemoteAddress() + "接入!");
                    } else if (key.isReadable()) {
                        // 客户端有数据传入，这里进行读取并处理
                        SocketChannel clientSocketChannel = (SocketChannel) key.channel();
                        System.out.println("客户端" + clientSocketChannel.getRemoteAddress() + ",发送了信息：");
                        clientSocketChannel.configureBlocking(false);
                        ByteBuffer byteBuffer = ByteBuffer.allocate(256);
                        int length = -1;
                        while ((length = clientSocketChannel.read(byteBuffer)) > 0) {
                            byteBuffer.flip();
                            System.out.println(StreamUtils.bytesToStr(byteBuffer.array(), byteBuffer.limit()));
                            byteBuffer.clear();
                        }
                        answerClient(clientSocketChannel, "收到你的信息");
                        // 否则客户端关闭就会导致死循环 打印“来自客户端94438417,发送了信息：”
                        System.out.println("客户端" + key.hashCode() + "关闭");
                        key.cancel();

                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 回复客户端
     */
    public void answerClient(SocketChannel clientSocketChannel, String content) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(content.getBytes(StandardCharsets.UTF_8));
        try {
            clientSocketChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        NIOServer server = new NIOServer();
        server.serverStart();
        server.serverPollingListen();
    }
}
