package com.hyj.netty.http.codec.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.JiBXException;

import java.io.StringReader;
import java.nio.charset.Charset;

public abstract class AbstractHttpXmlDecoder<T> extends MessageToMessageDecoder<T> {
    private IBindingFactory factory;
    private StringReader reader;
    private Class<?> clazz;
    private boolean isPrint;
    private final static String CHARSET_NAME = "UTF-8";
    private final static Charset UTF_8 = Charset.forName(CHARSET_NAME);

    public AbstractHttpXmlDecoder(Class<?> clazz) {
        this(clazz,false);
    }

    public AbstractHttpXmlDecoder(Class<?> clazz, boolean isPrint) {
        this.clazz = clazz;
        this.isPrint = isPrint;
    }

    protected Object decode0(ChannelHandlerContext ctx, ByteBuf body) throws JiBXException {
        factory = BindingDirectory.getFactory(clazz);
        String content = body.toString(UTF_8);
        if(isPrint){
            System.out.println("The body is : " + content);
        }
        reader = new StringReader(content);
        Object result = factory.createUnmarshallingContext().unmarshalDocument(reader);
        reader.close();
        reader = null;
        return result;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if(reader != null){
            reader.close();
            reader = null;
        }
    }
}
