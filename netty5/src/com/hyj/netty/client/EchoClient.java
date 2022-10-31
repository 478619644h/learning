package com.hyj.netty.client;

import com.hyj.netty.client.handler.DelimiteHandlerTestClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoClient {

    private String separator = "$_";

    public static void main(String[] args) throws InterruptedException {
        new EchoClient().connect("localhost",8080);
    }

    private void connect(String host,int port) throws InterruptedException {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
       try{
           Bootstrap b = new Bootstrap()
                   .group(workGroup)
                   .channel(NioSocketChannel.class)
                   .option(ChannelOption.TCP_NODELAY,true)
                   .handler(new LoggingHandler(LogLevel.INFO))
                   .handler(new ChannelInitializer<SocketChannel>() {
                       @Override
                       protected void initChannel(SocketChannel socketChannel) throws Exception {
                           socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled
                                   .copiedBuffer(separator.getBytes())))
                                   .addLast(new StringDecoder())
                                   .addLast(new DelimiteHandlerTestClientHandler());
                       }
                   });
           ChannelFuture future = b.connect(host, port).sync();
           future.channel().closeFuture().sync();
       } finally {
           workGroup.shutdownGracefully();
       }

    }

}
