package com.hyj.netty.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

import java.util.Date;

/**
 *测试TCP粘包问题
 */
public class TCPTimeServerHandler extends ChannelHandlerAdapter {

    private int counter;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(this);
        //ByteBuf byteBuf = (ByteBuf) msg;
        //String body = byteBuf.toString(CharsetUtil.UTF_8).substring(0,byteBuf.readableBytes()-System.getProperty("line.separator").length());
        //解决粘包的body 获取

        String body = (String)msg;
        System.out.println("the server receive order : " + body + " the counter is : " + ++counter);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)?new Date().toString():"BAD ORDER";
        currentTime += System.getProperty("line.separator");
        ctx.writeAndFlush(Unpooled.copiedBuffer(currentTime.getBytes()));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
