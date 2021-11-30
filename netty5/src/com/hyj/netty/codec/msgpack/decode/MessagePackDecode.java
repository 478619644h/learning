package com.hyj.netty.codec.msgpack.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

public class MessagePackDecode extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        final int length = byteBuf.readableBytes();
        MessagePack messagePack = new MessagePack();
        byte[] bytes = new byte[length];
        byteBuf.getBytes(byteBuf.readerIndex(),bytes,0,length);
        list.add(messagePack.read(bytes));
    }
}
