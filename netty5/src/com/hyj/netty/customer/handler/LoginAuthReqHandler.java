package com.hyj.netty.customer.handler;

import com.hyj.netty.customer.MessageType;
import com.hyj.netty.customer.protocol.Header;
import com.hyj.netty.customer.protocol.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;


public class LoginAuthReqHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.fireExceptionCaught(cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       ctx.writeAndFlush(buildLoginReq());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;

        //如是握手应答消息，判断是否认证成功
        if(message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP.value()){
            byte loginResult = (byte) message.getBody();
            if (loginResult != (byte)0){
                //握手失败
                ctx.close();
            } else {
                System.out.println("Login is Ok : " + message);
                ctx.fireChannelRead(msg);
            }

        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildLoginReq(){
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_REQ.value());
        message.setHeader(header);
        return message;
    }
}
