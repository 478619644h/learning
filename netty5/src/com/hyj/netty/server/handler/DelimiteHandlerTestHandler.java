package com.hyj.netty.server.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class DelimiteHandlerTestHandler extends ChannelHandlerAdapter {

    int counter;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("counter is : " + ++counter + " times receive client : [" + body + "]" );
        body = body + "$_";
        ctx.writeAndFlush(Unpooled.copiedBuffer(body.getBytes()));
    }


}
