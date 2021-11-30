package com.hyj.netty.codec.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.ArrayList;
import java.util.List;

public class ProtoBufTest {


    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq subscribeReq = create();

        System.out.println("encode :" + encode(subscribeReq));

        SubscribeReqProto.SubscribeReq req2 = decode(encode(subscribeReq));

        System.out.println("req :" + req2.toString());

        System.out.println("===================:[" + req2.getSubReqId() + "]");

        System.out.println("Assert equals :" + req2.equals(subscribeReq));


    }

    private static byte[] encode(SubscribeReqProto.SubscribeReq subscribeReq){
        return subscribeReq.toByteArray();
    }

    private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }

    private static SubscribeReqProto.SubscribeReq create(){
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqId(0);
        builder.setUserName("wsnd");
        builder.setProductName("Netty Book");
        List<String> addresses = new ArrayList<>();
        addresses.add("qin huang dao");
        addresses.add("nan jing");
        addresses.add("bei jing");
        builder.addAllAddress(addresses);

        return builder.build();
    }

}
