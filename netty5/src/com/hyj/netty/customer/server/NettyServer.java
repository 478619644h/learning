package com.hyj.netty.customer.server;

import com.hyj.netty.customer.NettyConstant;
import com.hyj.netty.customer.codec.NettyMessageDecoder;
import com.hyj.netty.customer.codec.NettyMessageEncoder;
import com.hyj.netty.customer.handler.HeartBeatRespHandler;
import com.hyj.netty.customer.handler.LoginAuthRespHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.nio.ByteOrder;

public class NettyServer {

    public void bind() throws Exception{

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            ch.pipeline()
                                    .addLast(new NettyMessageDecoder(1024 * 1024,4,4))
                                    .addLast(new NettyMessageEncoder())
                                    .addLast("readTimeoutHandler",new ReadTimeoutHandler(50))
                                    .addLast(new LoginAuthRespHandler())
                                    .addLast("HeartBeatRespHandler",new HeartBeatRespHandler());

                        }
                    });
            ChannelFuture future = b.bind(NettyConstant.PORT).sync();
            System.out.println("Netty server start ok ");
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        new NettyServer().bind();
    }
}
