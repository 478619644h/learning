package com.hyj.netty.codec.msgpack;

import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lesson1 {


    public static void main(String[] args) throws IOException {
        List<String> src = new ArrayList<String>();
        src.add("huang");
        src.add("feng");
        src.add("jing");
        MessagePack messagePack = new MessagePack();
        //serialize
        byte[] bytes = messagePack.write(src);

        //deserialize
        List<String> dst = messagePack.read(bytes, Templates.tList(Templates.TString));

        System.out.println(dst.get(1));

    }

}
