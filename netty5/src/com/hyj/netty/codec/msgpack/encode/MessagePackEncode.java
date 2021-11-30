package com.hyj.netty.codec.msgpack.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

import java.io.IOException;

/**
 * MessagePack 序列化工具实现的编码器
 * 优点：
 *  编解码高效 性能高
 *  序列化后的码流小
 *  支持多语言 c++，c#，java，python，ruby，haskell，Ocaml，Lua，Go，c
 */
public class MessagePackEncode extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) {
        MessagePack messagePack = new MessagePack();
        byte[] bytes = new byte[0];
        try {
            bytes = messagePack.write(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byteBuf.writeBytes(bytes);
    }


}
