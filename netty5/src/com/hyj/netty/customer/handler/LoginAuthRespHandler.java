package com.hyj.netty.customer.handler;

import com.hyj.netty.customer.MessageType;
import com.hyj.netty.customer.protocol.Header;
import com.hyj.netty.customer.protocol.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoginAuthRespHandler extends ChannelHandlerAdapter {

    private Map<String,Boolean> nodeCheck = new ConcurrentHashMap<>();

    private String[] whitekList = {"127.0.0.1","124.64.19.106"};

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        nodeCheck.remove(ctx.channel().remoteAddress().toString());
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;

        if(message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_REQ.value()){
            String nodeIndex = ctx.channel().remoteAddress().toString();
            NettyMessage loginResp = null;
            //重复登陆，拒绝
            if(nodeCheck.containsKey(nodeIndex)){
                loginResp = buildResponse((byte) -1);
            } else {
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();

                String ip = address.getAddress().getHostAddress();
                boolean isOk = false;
                for(String wip:whitekList){
                    if(wip.equals(ip)){
                        isOk = true;
                        break;
                    }
                }
                loginResp = isOk?buildResponse((byte) 0):buildResponse((byte) -1);
                if (isOk){
                    nodeCheck.put(nodeIndex,true);
                }
            }
            System.out.println("The login response is : " + loginResp + " body [" + loginResp.getBody() + "]" );
            ctx.writeAndFlush(loginResp);
        } else {
            ctx.fireChannelRead(msg);
        }

    }

    private NettyMessage buildResponse(byte result){
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_RESP.value());
        message.setHeader(header);
        message.setBody(result);
        return message;
    }

}
