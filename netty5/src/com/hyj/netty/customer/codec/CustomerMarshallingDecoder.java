package com.hyj.netty.customer.codec;

import com.hyj.netty.codec.marshalling.MarshallingCodecFactory;
import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.Unmarshaller;

import java.io.IOException;

public class CustomerMarshallingDecoder {

    private final Unmarshaller unmarshaller;

    public CustomerMarshallingDecoder() throws IOException {
        this.unmarshaller = MarshallingCodecFactory.buildUnMarshalling();
    }

    public Object decode(ByteBuf in) throws Exception {
        int objectSize = in.readInt();
        ByteBuf buf = in.slice(in.readerIndex(),objectSize);

        try{
            unmarshaller.start(new ChannelBufferByteInput(buf));
            Object object = unmarshaller.readObject();
            in.readerIndex(in.readerIndex()+objectSize);
            return object;
        } finally {
            unmarshaller.close();
        }

    }

}
