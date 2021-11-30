package com.hyj.netty.client.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class DelimiteHandlerTestClientHandler extends ChannelHandlerAdapter {

    private String separator = "$_";

    int counter;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 100; i++){
            ctx.writeAndFlush(Unpooled.copiedBuffer(("I am big 11111 " + separator).getBytes()));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("counter is : " + ++counter + " times receive server :[" + body + "]");
    }
}
