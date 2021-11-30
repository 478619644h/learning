package com.hyj.netty.customer.server;

import com.hyj.netty.customer.NettyConstant;
import com.hyj.netty.customer.codec.NettyMessageDecoder;
import com.hyj.netty.customer.codec.NettyMessageEncoder;
import com.hyj.netty.customer.handler.HeartBeatReqHandler;
import com.hyj.netty.customer.handler.LoginAuthReqHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NettyClient {


    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    EventLoopGroup group = new NioEventLoopGroup();

    public void connect(int port,String host) throws InterruptedException {
        try{
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new NettyMessageDecoder(1024 * 1024,4,4))
                                    .addLast("MessageEncoder",new NettyMessageEncoder())
                                    .addLast("readTimeOutHandler",new ReadTimeoutHandler(50))
                                    .addLast("LoginAuthHandler",new LoginAuthReqHandler())
                                    .addLast("HeartBeatHandler",new HeartBeatReqHandler());
                        }
                    });

            ChannelFuture future = b.connect(new InetSocketAddress(host, port),
                    new InetSocketAddress(NettyConstant.LOCALIP,NettyConstant.LOCAL_PORT)).sync();

            future.channel().closeFuture().sync();
        } finally {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        connect(NettyConstant.PORT,NettyConstant.REMOTEIP);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new NettyClient().connect(NettyConstant.PORT,NettyConstant.REMOTEIP);
    }
}
