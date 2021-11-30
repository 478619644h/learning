package com.hyj.netty.http.server;

import com.hyj.netty.http.codec.decode.HttpXmlResponseDecoder;
import com.hyj.netty.http.codec.encode.HttpXmlRequestEncoder;
import com.hyj.netty.http.entity.Order;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

public class HttpXmlClient {

    public static void main(String[] args) throws InterruptedException {
        new HttpXmlClient().connect(8080);
    }


    private void connect(int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast("http-decoder",new HttpResponseDecoder())
                                    .addLast("http-aggregator",new HttpObjectAggregator(65535))
                                    .addLast("xml-decoder",new HttpXmlResponseDecoder(Order.class))
                                    .addLast("http-encoder",new HttpRequestEncoder())
                                    .addLast("xml-encoder",new HttpXmlRequestEncoder())
                                    .addLast("xmlClientHandler", new HttpXmlClientHandle());

                        }
                    });
            ChannelFuture future = b.connect(new InetSocketAddress(port)).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }

    }
}
