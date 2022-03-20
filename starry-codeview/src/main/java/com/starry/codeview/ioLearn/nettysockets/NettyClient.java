package com.starry.codeview.ioLearn.nettysockets;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public void start() {
        EventLoopGroup eventGroup = new DefaultEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventGroup)
                .channel(NioSocketChannel.class)
                .connect("127.0.0.1", 6700);

    }
}
