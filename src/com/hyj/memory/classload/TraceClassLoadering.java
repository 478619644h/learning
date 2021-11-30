package com.hyj.memory.classload;

/**
 *
 * jvm参数 -XX:+TraceClassLoading 用于追踪类的加载信息并打印出来
 * 分析项目启动为什么这么慢，快速定位自己的类有没有被加载
 * rt.jar jdk 出厂自带的，最高级别的类加载器要加载的！
 *
 */
public class TraceClassLoadering {

    public static void main(String[] args) {
        System.out.println(child.a);
    }

}


class parent{
    public static String a = "parent";
    static {
        System.out.println("this is parent static block");
    }
}

class child extends parent{
    public static String a = "child";
    static {
        System.out.println("this is child static block");
    }
}