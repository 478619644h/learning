package com.hyj.netty.http.codec.encode;

import com.hyj.netty.http.codec.decode.HttpXmlResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.util.List;

public class HttpXmlResponseEncoder extends AbstractHttpXmlEncoder<HttpXmlResponse> {
    @Override
    protected void encode(ChannelHandlerContext ctx, HttpXmlResponse msg, List<Object> out) throws Exception {
        ByteBuf body = encode0(ctx, msg.getResult());
        FullHttpResponse response = msg.getHttpResponse();
        if(response == null){
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,body);
        } else {
            response = new DefaultFullHttpResponse(msg.getHttpResponse().protocolVersion(),
                    msg.getHttpResponse().status(),body);
        }
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/xml");
        HttpHeaderUtil.setContentLength(response,body.readableBytes());
        out.add(response);
    }
}
