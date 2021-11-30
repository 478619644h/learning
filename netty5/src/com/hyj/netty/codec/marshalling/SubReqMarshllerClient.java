package com.hyj.netty.codec.marshalling;

import com.hyj.netty.codec.protobuf.SubscribeReqProto;
import com.hyj.netty.codec.protobuf.SubscribeRespProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.ArrayList;
import java.util.List;

public class SubReqMarshllerClient {
    public static void main(String[] args) throws InterruptedException {
        new SubReqMarshllerClient().connect("127.0.0.1",8080);
    }

    private void connect(String host,int port) throws InterruptedException {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(MarshallingCodecFactory.buildMarshallingDecodeFactory())
                                    .addLast(MarshallingCodecFactory.buildMarshallingEncodeFactory())
                                    .addLast(new ChannelHandlerAdapter(){
                                        @Override
                                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                            cause.printStackTrace();
                                            ctx.close();
                                        }

                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                           for (int i = 1; i <= 100; i++){
                                               ctx.write(req(i));
                                           }
                                           ctx.flush();
                                        }

                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            System.out.println("receive server respons : " + msg);
                                            SubscribeRespProto.SubscribeResp subscribeResp = ( SubscribeRespProto.SubscribeResp ) msg;
                                            //System.out.println("===================== : [" + subscribeResp.getSubReqId() + "]");
                                        }

                                        @Override
                                        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                            ctx.flush();
                                        }

                                        private SubscribeReqProto.SubscribeReq req(int id){
                                            List<String> addresses = new ArrayList<>();
                                            addresses.add("qin huang dao");
                                            addresses.add("nan jing");
                                            addresses.add("bei jing");
                                           return SubscribeReqProto.SubscribeReq.newBuilder()
                                                    .setSubReqId(id)
                                                    .setUserName("wsnd")
                                                    .setProductName("Netty Book For Protobuf")
                                                    .addAllAddress(addresses).build();
                                        }
                                    });
                        }
                    });
            ChannelFuture sync = b.connect(host, port).sync();
            sync.channel().closeFuture().sync();
        } finally {
            workGroup.shutdownGracefully();
        }

    }
}
