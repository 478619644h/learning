package com.hyj.netty.codec.marshalling;


import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.*;

import java.io.IOException;

public class MarshallingCodecFactory {


    public static MarshallingEncoder buildMarshallingEncodeFactory(){
        final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration marshallingConfiguration = new MarshallingConfiguration();
        marshallingConfiguration.setVersion(5);
        MarshallerProvider marshallerProvider = new DefaultMarshallerProvider(factory,marshallingConfiguration);
        MarshallingEncoder marshallingEncoder = new MarshallingEncoder(marshallerProvider);
        return marshallingEncoder;
    }

    public static MarshallingDecoder buildMarshallingDecodeFactory(){
        final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration marshallingConfiguration = new MarshallingConfiguration();
        marshallingConfiguration.setVersion(5);
        DefaultUnmarshallerProvider defaultUnmarshallerProvider = new DefaultUnmarshallerProvider(factory, marshallingConfiguration);
        MarshallingDecoder marshallingDecoder = new MarshallingDecoder(defaultUnmarshallerProvider);
        return marshallingDecoder;
    }

    public static Marshaller buildMarshalling() throws IOException {
        final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration marshallingConfiguration = new MarshallingConfiguration();
        marshallingConfiguration.setVersion(5);
        return factory.createMarshaller(marshallingConfiguration);
    }

    public static Unmarshaller buildUnMarshalling() throws IOException {
        final MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration marshallingConfiguration = new MarshallingConfiguration();
        marshallingConfiguration.setVersion(5);
        return factory.createUnmarshaller(marshallingConfiguration);
    }

    public static void main(String[] args) {
        buildMarshallingDecodeFactory();
    }

}
