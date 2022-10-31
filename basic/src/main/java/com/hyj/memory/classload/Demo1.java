package com.hyj.memory.classload;

import java.util.UUID;

/**
 * final 的变量会被放到常量池中 不需要在对应的类中去取 类不需要被初始化
 */
public class Demo1 {

    public static void main(String[] args) {
        //System.out.println(child1.a);
        System.out.println(child2.a);
    }


}

class parent1{
    public static String a = "parent";
    static {
        System.out.println("parent");
    }
}

class child1 extends parent1{
    /**
     * final 常量在编译阶段的时候 常量池；
     * 这个代码中将常量放到了 child1 的常量池中。之后 child1与parent1 就没有关系了
     */
    public final static String a = "child";
    static {
        System.out.println("child");
    }
}

class child2 extends parent1{
    /**
     * 当一个常量的值并非编译期间可以确定的，那这个值就不会被放到调用类的常量池中！
     * 程序运行期间的时候，会主动使用常量所在的类
     */
    public final static String a = UUID.randomUUID().toString();
    static {
        System.out.println("child2");
    }
}