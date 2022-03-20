package com.starry.codeview.ioLearn.nettysockets;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {


    public void startServer() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        //创建netty服务器
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)//设置主从线程组
                    .channel(NioServerSocketChannel.class)//设置通道
                    .option(ChannelOption.SO_BACKLOG, 128)// 设置线程队列连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new NettyServerInitializer());// 子处理器，用于处理workerGroup中的操作

            //启动server
            ChannelFuture channelFuture = serverBootstrap.bind(6700).sync();
            //监听关闭channel事件
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();//关闭主线程
            workerGroup.shutdownGracefully();//关闭从线程
        }
    }

    public static void main(String[] args) {
        NettyServer server = new NettyServer();
        server.startServer();

    }
}
