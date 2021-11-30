package com.hyj.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class NettyTest {

    public static void main(String[] args) {

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        EventLoopGroup bosskGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        serverBootstrap.group(bosskGroup,workerGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.bind(new InetSocketAddress(8888));
        serverBootstrap.register();


    }

}
