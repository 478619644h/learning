package com.hyj.netty.customer.handler;

import com.hyj.netty.customer.MessageType;
import com.hyj.netty.customer.protocol.Header;
import com.hyj.netty.customer.protocol.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HeartBeatReqHandler extends ChannelHandlerAdapter {

    private volatile ScheduledFuture<?> heartBeat;

    private AtomicInteger heartBeatCount = new AtomicInteger(0);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if(heartBeat != null){
            heartBeat.cancel(true);
            heartBeat = null;
        }
        ctx.fireExceptionCaught(cause);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        heartBeatCount.set(0);
        if(message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP.value()){
            heartBeat = ctx.executor().scheduleAtFixedRate(
                new HeartBeatReqHandler.HeartBeatTask(ctx),0,5000, TimeUnit.MILLISECONDS
            );

        } else if(message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTBEAT_RESP.value()){
            System.out.println("Client receive server heart beat message : ---> " + message);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private class HeartBeatTask implements Runnable{

        private final ChannelHandlerContext ctx;

        public HeartBeatTask(final ChannelHandlerContext ctx){
            this.ctx = ctx;
        }

        @Override
        public void run() {
            if(heartBeatCount.intValue() == 5){
                if(heartBeat != null){
                    heartBeat.cancel(true);
                    heartBeat = null;
                }
                ctx.close();
                System.out.println("heart beat count out of 5.......");
                return;
            }
            NettyMessage message = buildHeatBeat();
            System.out.println("Client send heart beat message to server : ---> " + message) ;
            ctx.writeAndFlush(message);
            heartBeatCount.getAndIncrement();

        }

        private NettyMessage buildHeatBeat(){
            NettyMessage message = new NettyMessage();
            Header header = new Header();
            header.setType(MessageType.HEARTBEAT_REQ.value());
            message.setHeader(header);
            return message;
        }

    }
}
