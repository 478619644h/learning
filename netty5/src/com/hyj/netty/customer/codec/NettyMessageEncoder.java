package com.hyj.netty.customer.codec;

import com.hyj.netty.customer.protocol.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage> {

    CustomerMarshallingEncoder customerMarshallingEncoder;

    public NettyMessageEncoder() throws IOException {
        this.customerMarshallingEncoder = new CustomerMarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, List<Object> out) throws Exception {
        if (msg == null || msg.getHeader() == null){
            throw new Exception("The encode message is null");
        }

        ByteBuf sendBuf = Unpooled.buffer();
        sendBuf.writeInt(msg.getHeader().getCrcCode());
        sendBuf.writeInt(msg.getHeader().getLength());
        sendBuf.writeLong(msg.getHeader().getSessionID());
        sendBuf.writeByte(msg.getHeader().getType());
        sendBuf.writeByte(msg.getHeader().getPriority());

        sendBuf.writeInt(msg.getHeader().getAttachment().size());

        String key = null;
        byte[] keyArray = null;
        Object value =  null;

        for (Map.Entry<String, Object> param : msg.getHeader().getAttachment().entrySet()) {
            key = param.getKey();
            keyArray = key.getBytes("UTF-8");

            sendBuf.writeInt(keyArray.length);
            sendBuf.writeBytes(keyArray);

            value = param.getValue();
            customerMarshallingEncoder.encode(value,sendBuf);
        }
        key = null;
        keyArray = null;
        value =  null;

        if(msg.getBody() != null){
            customerMarshallingEncoder.encode(msg.getBody(),sendBuf);
        } else {
            sendBuf.writeInt(0);
        }
        //之所以-8 是 减去 head中魔数 的 4bytes 再减去长度字段的 4bytes 只表示消息体的长度
        sendBuf.setInt(4,sendBuf.readableBytes()-8);
        out.add(sendBuf);

    }
}
