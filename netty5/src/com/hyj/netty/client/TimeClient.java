package com.hyj.netty.client;

import com.hyj.netty.client.handler.TCPTimeClientHandler;
import com.hyj.netty.client.handler.TimeClientHandler;
import com.hyj.netty.customer.NettyConstant;
import com.hyj.netty.server.handler.TCPTimeServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.net.InetSocketAddress;

public class TimeClient {

    public static void main(String[] args) {
        int port = NettyConstant.PORT;
        String host = NettyConstant.REMOTEIP;
        if(args != null && args.length > 1){
            port = Integer.parseInt(args[0]);
            host = args[1];
        }

        try {
            new TimeClient().connect(host,port);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void connect(String host,int port) throws InterruptedException {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.channel(NioSocketChannel.class)
                    .group(workGroup)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //socketChannel.pipeline().addLast(new TimeClientHandler());
                            //测试粘包
                            socketChannel.pipeline()
                                    .addLast(new LineBasedFrameDecoder(1024))
                                    .addLast(new StringDecoder())
                                    .addLast(new TCPTimeClientHandler());
                        }
                    });

            //发起异步连接操作
            ChannelFuture future = b.connect(new InetSocketAddress(NettyConstant.REMOTEIP,NettyConstant.PORT)).sync();

            future.channel().closeFuture().sync();
        } finally {
            workGroup.shutdownGracefully();
        }

    }
}
