package com.hyj.netty.http.codec.decode;

import io.netty.handler.codec.http.FullHttpResponse;

public class HttpXmlResponse {
    private FullHttpResponse httpResponse;
    private Object result;

    public HttpXmlResponse(FullHttpResponse httpResponse, Object result) {
        this.httpResponse = httpResponse;
        this.result = result;
    }

    public final FullHttpResponse getHttpResponse() {
        return httpResponse;
    }

    public final HttpXmlResponse setHttpResponse(FullHttpResponse httpResponse) {
        this.httpResponse = httpResponse;
        return this;
    }

    public final Object getResult() {
        return result;
    }

    public final HttpXmlResponse setResult(Object result) {
        this.result = result;
        return this;
    }
}
