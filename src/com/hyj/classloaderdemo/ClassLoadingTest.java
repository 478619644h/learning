package com.hyj.classloaderdemo;

public class ClassLoadingTest {

    public static void main(String[] args) {
        System.out.println(Child.str);
        while (true){
            System.gc();
        }
    }


}


class Parent{
    public static int a = 0;

    static {
        System.out.println("this is parent static block");
    }

}


class Child extends Parent{
    public static String str = "a";

    static {
        System.out.println("this is child static block");
    }
}