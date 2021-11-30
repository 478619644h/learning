package com.hyj.netty.http.server;

import com.hyj.netty.http.codec.decode.HttpXmlResponse;
import com.hyj.netty.http.codec.encode.HttpXmlRequest;
import com.hyj.netty.http.entity.Address;
import com.hyj.netty.http.entity.Customer;
import com.hyj.netty.http.entity.Order;
import com.hyj.netty.http.entity.Shipping;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HttpXmlClientHandle extends SimpleChannelInboundHandler<HttpXmlResponse> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Order order = new Order();
        order.setOrderNumber(123);
        Address address = new Address();
        address.setStreet1("热血八番街");
        address.setCity("银月");
        address.setCountry("天星");
        order.setBillTo(address);
        order.setShipTo(address);
        order.setShipping(Shipping.INTERNATIONAL_EXPRESS);
        Customer customer = new Customer();
        customer.setFirstName("浩");
        customer.setLastName("李");
        customer.setCustomerNumber(1231312);
        order.setCustomer(customer);
        HttpXmlRequest request = new HttpXmlRequest(null, order);
        ctx.writeAndFlush(request);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, HttpXmlResponse msg) throws Exception {
        System.out.println("The client receive response of http header is : "
                + msg.getHttpResponse().headers().names());
        System.out.println("The client receive response of http body is : "
                + msg.getResult());
    }
}
