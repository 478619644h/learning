package com.hyj.netty.server;

import com.hyj.netty.codec.protobuf.SubscribeReqProto;
import com.hyj.netty.codec.protobuf.SubscribeRespProto;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


public class SubReqServer {

    public static void main(String[] args) throws InterruptedException {
        new SubReqServer().bind(8080);
    }


    private void bind(int port) throws InterruptedException {
        NioEventLoopGroup bassGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bassGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new ProtobufVarint32FrameDecoder())
                                    .addLast(new ProtobufDecoder(SubscribeReqProto.SubscribeReq.getDefaultInstance()))
                                    .addLast(new ProtobufVarint32LengthFieldPrepender())
                                    .addLast(new ProtobufEncoder())
                                    .addLast(new ChannelHandlerAdapter() {

                                        @Override
                                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                            cause.printStackTrace();
                                            ctx.close();
                                        }

                                        @Override
                                        public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
                                            SubscribeReqProto.SubscribeReq subscribeReq = (SubscribeReqProto.SubscribeReq) o;
                                            if("wsnd".equalsIgnoreCase(subscribeReq.getUserName())){
                                                System.out.println("server receive client subscribe req :[" + o.toString() + "];");
                                                channelHandlerContext.writeAndFlush(resp(subscribeReq.getSubReqId()));
                                            }
                                        }

                                        private SubscribeRespProto.SubscribeResp resp(int subReqId){
                                            return SubscribeRespProto.SubscribeResp.newBuilder()
                                                    .setSubReqId(subReqId)
                                                    .setRespCode(1)
                                                    .setDesc("Netty Book order succeed, 3 days later,send to the designated address")
                                                    .build();
                                        }


                                    });
                        }
                    });
            ChannelFuture future = b.bind(port).sync();
            future.channel().closeFuture().sync();
        } finally {
            bassGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
