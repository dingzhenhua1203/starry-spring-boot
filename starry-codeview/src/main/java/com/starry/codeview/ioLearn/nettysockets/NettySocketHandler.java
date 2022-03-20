package com.starry.codeview.ioLearn.nettysockets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 自定义handler实现netty的
 */
public class NettySocketHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead" + ctx);
        ByteBuf bytebuf = (ByteBuf) msg;
        System.out.println("客户端" + ctx.channel().remoteAddress() + "发送消息：" + bytebuf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // ctx.writeAndFlush("欢迎接入".getBytes(StandardCharsets.UTF_8));
        ctx.writeAndFlush(Unpooled.copiedBuffer("欢迎接入", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        // 捕获到异常进行处理
        ctx.close();
    }
}
