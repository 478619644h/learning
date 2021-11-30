package com.hyj.netty.http.server;

import com.hyj.netty.http.codec.decode.HttpXmlRequestDecoder;
import com.hyj.netty.http.codec.encode.HttpXmlResponseEncoder;
import com.hyj.netty.http.entity.Order;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class HttpXmlServer {

    public static void main(String[] args) throws Exception {
        new HttpXmlServer().run(8080);
    }

    public void run(final int port) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast("http-decoder",new HttpRequestDecoder())
                                    .addLast("http-aggregator",new HttpObjectAggregator(65535))
                                    .addLast("xml-decoder",new HttpXmlRequestDecoder(Order.class,true))
                                    .addLast("http-encoder",new HttpResponseEncoder())
                                    .addLast("xml-encoder", new HttpXmlResponseEncoder())
                                    .addLast("xmlServerHandler",new HttpXmlServerhandler());

                        }
                    });
            ChannelFuture future = b.bind(new InetSocketAddress("0.0.0.0",port)).sync();
            System.out.println("HTTP 订 购 服 务 器 启 动，网 址 是 ：" + InetAddress.getLocalHost().getHostAddress() + ":" + port);
            future.channel().closeFuture().sync();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
