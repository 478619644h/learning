package com.hyj.netty.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.logging.Log4JLoggerFactory;

import java.util.logging.Logger;

/**
 *  tcp粘包
 */
public class TCPTimeClientHandler extends ChannelHandlerAdapter {

    private int counter;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.close();
    }

    //连接成功后会调用这个方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = null;
        for (int i = 0; i < 100; i++){
            byteBuf = Unpooled.copiedBuffer(("QUERY TIME ORDER" + System.getProperty("line.separator")) .getBytes());
            ctx.writeAndFlush(byteBuf);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //ByteBuf byteBuf = (ByteBuf) msg;
        String body = (String) msg;
        System.out.println("Now is : " + body + " the counter is : " + ++counter);
    }
}
