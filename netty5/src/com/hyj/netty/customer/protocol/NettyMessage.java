package com.hyj.netty.customer.protocol;

public final class NettyMessage {

    private Header header;

    private Object body;

    public Header getHeader() {
        return header;
    }

    public NettyMessage setHeader(Header header) {
        this.header = header;
        return this;
    }

    public Object getBody() {
        return body;
    }

    public NettyMessage setBody(Object body) {
        this.body = body;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NettyMessage{");
        sb.append("header=").append(header);
        sb.append(", body=").append(body);
        sb.append('}');
        return sb.toString();
    }
}
