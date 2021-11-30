package com.hyj.memory.classload;

public class ClassLoaderKinds {

    public static void main(String[] args) {
        //jdk 自带的
        Object o = new Object();
        ClassLoaderKinds app = new ClassLoaderKinds();




        // null 在这里并不代表没有，只是Java触及不到！
        System.out.println(o.getClass().getClassLoader());
        //AppClassLoader
        System.out.println(app.getClass().getClassLoader());
        //ExtClassLoader
        System.out.println(app.getClass().getClassLoader().getParent());
        //null
        System.out.println(app.getClass().getClassLoader().getParent().getParent());

        // jvm 中有机制可以保护自己的安全；
        // 双亲委派机制 ： 一层一层的让父类去加载，如果顶层的加载器不能加载，然后再向下类推
        // Demo01
        // AppClassLoader 03
        // ExtClassLoader 02
        // BootStrap (最顶层) 01 java.lang.String rt.jar
        // 双亲委派机制 可以保护java的核心类不会被自己定义的类所替代
        while(true){

        }
    }
}
