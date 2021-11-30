package com.hyj.netty.http.server;

import com.hyj.netty.http.codec.decode.HttpXmlResponse;
import com.hyj.netty.http.codec.encode.HttpXmlRequest;
import com.hyj.netty.http.entity.Address;
import com.hyj.netty.http.entity.Order;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.ArrayList;
import java.util.List;

public class HttpXmlServerhandler extends SimpleChannelInboundHandler<HttpXmlRequest> {


    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, HttpXmlRequest httpXmlRequest) throws Exception {
        HttpRequest request = httpXmlRequest.getRequest();
        Order body = (Order) httpXmlRequest.getBody();
        System.out.println("Http server receive request : " + body);
        doBusiness(body);
        ChannelFuture future = channelHandlerContext.writeAndFlush(new HttpXmlResponse(null, body));
        if (HttpHeaderUtil.isKeepAlive(request)) {
            future.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    channelHandlerContext.close();
                }
            });
        }
    }

    private void doBusiness(Order order) {
        order.getCustomer().setFirstName("狄");
        order.getCustomer().setLastName("仁杰");
        List<String> midNames = new ArrayList<>();
        midNames.add("李元芳");
        order.getCustomer().setMiddleName(midNames);
        Address address = order.getBillTo();
        address.setCity("洛阳");
        address.setCountry("大唐");
        address.setState("河南道");
        address.setPostCode("123456");
        order.setBillTo(address);
        order.setShipTo(address);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer("失败: " +
                "\r\n", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
        ctx.writeAndFlush(response);
    }

}
