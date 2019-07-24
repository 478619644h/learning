package com.hyj.classloaderdemo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TestClass {
    public static void main(String[] args) throws IOException {
       /* InputStream is = new FileInputStream("/Users/huangyujian/work/zhyxworkforidea/leaning/out/production/leaning/com/hyj/classloaderdemo/TestClass.class");
        byte[] a = new byte[is.available()];
        is.read(a);
        is.close();
        System.out.println("sssdfsdfadf");
        System.out.println(System.currentTimeMillis());
        JavaClassExecuter.execute(a);
        System.out.println(System.getenv());
*/

       Integer a = 222;
       Integer b = 222;
       Integer c = 2;
       Integer v = 444;
       Integer g = 444;
       Long s = 444l;
       System.out.println(a==b);
       System.out.println(v == g);
       System.out.println(c==(a+b));
       System.out.println(c.equals((a+b)));
       System.out.println(s == (a+b));
        System.out.println(s.equals(a+b));
    }
}
