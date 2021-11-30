package com.hyj.netty.server;

import com.hyj.netty.codec.msgpack.decode.MessagePackDecode;
import com.hyj.netty.codec.msgpack.encode.MessagePackEncode;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoTestCodesForMassagePackServer {

    public static void main(String[] args) throws InterruptedException {
        new EchoTestCodesForMassagePackServer().bind(8080);
    }


    private void bind(int port) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
       try{
           ServerBootstrap b = new ServerBootstrap();
           b.group(bossGroup,workGroup)
                   .channel(NioServerSocketChannel.class)
                   .option(ChannelOption.SO_BACKLOG,100)
                   .handler(new LoggingHandler(LogLevel.INFO))
                   .childHandler(new ChannelInitializer<SocketChannel>() {
                       @Override
                       protected void initChannel(SocketChannel socketChannel) throws Exception {
                           socketChannel.pipeline()
                                   //消息头中增加2个字节的消息长度
                                   .addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535,0,2,0,2))
                                   //
                                   .addLast("msgpack decode",new MessagePackDecode())
                                   //消息头中增加2个字节的消息长度
                                   .addLast("frameEncoder",new LengthFieldPrepender(2))
                                   .addLast("msgpack encode",new MessagePackEncode())
                                   .addLast(new EchoHandler());

                       }
                   });
           ChannelFuture future = b.bind(port).sync();
           future.channel().closeFuture().sync();
       } finally {
           bossGroup.shutdownGracefully();
           workGroup.shutdownGracefully();
       }


    }

    class EchoHandler extends ChannelHandlerAdapter{
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("server receive msg : " + msg);
            ctx.writeAndFlush(msg);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }
    }
}
