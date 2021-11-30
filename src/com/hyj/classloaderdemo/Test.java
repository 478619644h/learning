package com.hyj.classloaderdemo;

import java.io.FileInputStream;
import java.io.InputStream;

public class Test {
    public static void main(String[] args) throws Exception {
        InputStream is = new FileInputStream("/Users/huangyujian/work/gitwork/learning/out/com/hyj/classloaderdemo/TestClass.class");
        byte[] a = new byte[is.available()];
        is.read(a);
        is.close();
        System.out.println(System.currentTimeMillis());
        String execute = JavaClassExecuter.execute(a);
        System.out.println(execute);
        System.out.println(System.class);
    }
}
