package com.hyj.netty.http.codec.decode;

import com.hyj.netty.http.codec.encode.HttpXmlRequest;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.List;

public class HttpXmlRequestDecoder extends AbstractHttpXmlDecoder<FullHttpRequest> {


    public HttpXmlRequestDecoder(Class<?> clazz) {
        this(clazz,false);
    }

    public HttpXmlRequestDecoder(Class<?> clazz, boolean isPrint) {
        super(clazz, isPrint);
    }

    @Override
    protected void decode(ChannelHandlerContext arg0,
                          FullHttpRequest arg1, List<Object> arg2) throws Exception {
        if(!arg1.decoderResult().isSuccess()){
            sendError(arg0,HttpResponseStatus.BAD_REQUEST);
            return;
        }

        HttpXmlRequest request = new HttpXmlRequest(arg1, decode0(arg0, arg1.content()));
        arg2.add(request);

    }

    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status){
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                status,
                Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
