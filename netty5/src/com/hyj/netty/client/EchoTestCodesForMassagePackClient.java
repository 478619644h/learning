package com.hyj.netty.client;

import com.hyj.netty.codec.msgpack.decode.MessagePackDecode;
import com.hyj.netty.codec.msgpack.encode.MessagePackEncode;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoTestCodesForMassagePackClient {

    public static void main(String[] args) throws InterruptedException {
        new EchoTestCodesForMassagePackClient().connect("127.0.0.1",8080);
    }

    private void connect(String host,int port) throws InterruptedException {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap()
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .group(workGroup)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535,0,2,0,2))
                                    .addLast("msgpack decode",new MessagePackDecode())
                                    .addLast("frameEncoder",new LengthFieldPrepender(2))
                                    .addLast("msgpack encode",new MessagePackEncode())
                                    .addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture future = b.connect(host, port).sync();
            future.channel().closeFuture().sync();

        } finally {
            workGroup.shutdownGracefully();
        }

    }

    class EchoClientHandler extends ChannelHandlerAdapter{
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            UserInfo[] userInfos = userInfos();
            for (int i = 0; i < userInfos.length; i++) {
                ctx.writeAndFlush(userInfos[i]);
            }
            ctx.flush();
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("client receive msg : " + msg);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        private UserInfo[] userInfos(){
            UserInfo[] userInfos = new UserInfo[100];
            for (int i = 0; i < 100; i++){
                UserInfo userInfo = new UserInfo();
                userInfo.setAge(i);
                userInfo.setUserName("qwer" + i);
                userInfos[i] = userInfo;
            }
            return userInfos;
        }
    }


}
