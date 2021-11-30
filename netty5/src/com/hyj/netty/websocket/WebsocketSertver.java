package com.hyj.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;

import java.util.Date;

public class WebsocketSertver {

    private void bind(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup wokerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,wokerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast("http-codec",new HttpServerCodec())
                                    .addLast("aggregator",new HttpObjectAggregator(65535))
                                    .addLast("http-chunked",new ChunkedWriteHandler())
                                    .addLast("handler", new SimpleChannelInboundHandler() {

                                        private WebSocketServerHandshaker handshaker;
                                        @Override
                                        protected void messageReceived(ChannelHandlerContext ctx,
                                                                       Object msg) throws Exception {

                                            if(msg instanceof FullHttpRequest){
                                                handleHttpRequest(ctx, (FullHttpRequest) msg);
                                            } else if(msg instanceof WebSocketFrame){
                                                handleWebSocketFrame(ctx,(WebSocketFrame) msg);
                                            }

                                        }

                                        private void handleHttpRequest(ChannelHandlerContext ctx,
                                                                        FullHttpRequest req) throws Exception{

                                            if(!req.decoderResult().isSuccess()||(!"websocket"
                                                    .equalsIgnoreCase(req.headers().get("Upgrade").toString()))){
                                                sendHttpResponse(ctx,req,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                                                        HttpResponseStatus.BAD_REQUEST));
                                            }

                                            WebSocketServerHandshakerFactory webSocketServerHandshakerFactory
                                                    = new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket",null,false);

                                            handshaker = webSocketServerHandshakerFactory.newHandshaker(req);

                                            if(handshaker == null){
                                                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
                                            } else {
                                                handshaker.handshake(ctx.channel(),req);
                                            }

                                        }

                                        private void handleWebSocketFrame(ChannelHandlerContext ctx,WebSocketFrame frame){

                                            //关闭链路的指令
                                            if(frame instanceof CloseWebSocketFrame){
                                                handshaker.close(ctx.channel(),((CloseWebSocketFrame) frame).retain());
                                                return;
                                            }

                                            //是否是ping消息
                                            if(frame instanceof PingWebSocketFrame){
                                                ctx.channel().write(
                                                        new PongWebSocketFrame(frame.content().retain())
                                                );
                                                return;
                                            }

                                            //仅支持文本消息 不支持二进制消息
                                            if(!(frame instanceof TextWebSocketFrame)){
                                                throw new UnsupportedOperationException(String.format(
                                                        "%s frame types not supported",frame.getClass().getName()
                                                ));
                                            }

                                            String request = ((TextWebSocketFrame) frame).text();
                                            System.out.println(String.format("%s received %s",ctx.channel(),request));

                                            ctx.channel().write(
                                                    new TextWebSocketFrame(request+" , 欢迎使用 Netty Websocket 服务，现在时刻" +
                                                            "： " + new Date().toString())

                                            );
                                        }

                                        private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req,
                                                                      FullHttpResponse res){
                                            if(res.status().code() != 200){
                                                ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
                                                res.content().writeBytes(buf);
                                                buf.release();
                                                HttpHeaderUtil.setContentLength(res,res.content().readableBytes());
                                            }

                                            ChannelFuture future = ctx.channel().writeAndFlush(res);
                                            if(!HttpHeaderUtil.isKeepAlive(req)||res.status().code() != 200){
                                                future.addListener(ChannelFutureListener.CLOSE);
                                            }
                                        }

                                        @Override
                                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                            cause.printStackTrace();
                                            ctx.close();
                                        }

                                        @Override
                                        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                            ctx.flush();
                                        }
                                    });

                        }
                    });
            ChannelFuture f = b.bind(port).sync();
            System.out.println("the websocket server started ...");
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            wokerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new WebsocketSertver().bind(8080);
    }

}
