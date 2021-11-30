package com.hyj.netty.http.xml;

import org.jibx.binding.generator.BindGen;
import org.jibx.runtime.JiBXException;

import java.io.IOException;

public class PojoToXml {

    public static void main(String[] args) throws JiBXException, IOException {
        String[] params = new String[4];
        params[0] = "-t";
        params[1] = "/Users/huangyujian/work/gitwork/learning/netty5/src/com/hyj/netty/http/xml";
        params[2] = "-v";
        params[3] = "com.hyj.netty.http.entity.Order";
        BindGen.main(params);

    }

}
