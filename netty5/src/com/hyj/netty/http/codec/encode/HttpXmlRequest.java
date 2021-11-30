package com.hyj.netty.http.codec.encode;

import io.netty.handler.codec.http.FullHttpRequest;

public class HttpXmlRequest {

    private FullHttpRequest request;
    private Object body;

    public HttpXmlRequest(FullHttpRequest request, Object body) {
        this.request = request;
        this.body = body;
    }

    public final FullHttpRequest getRequest() {
        return request;
    }

    public final HttpXmlRequest setRequest(FullHttpRequest request) {
        this.request = request;
        return this;
    }

    public final Object getBody() {
        return body;
    }

    public final HttpXmlRequest setBody(Object body) {
        this.body = body;
        return this;
    }
}
