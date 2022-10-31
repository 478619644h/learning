package com.hyj.classloaderdemo;

/**
 * 为了多次载入执行类而加入的加载器
 * 把defineClass方法开放出来，只有外部显示的调用才会使用loadByte方法
 * 由虚拟机调用时，仍然按照原来的双亲委托规则使用loadclass方法进行类加载
 */
public class HotSwapClassLoader extends ClassLoader {

    public HotSwapClassLoader() {
        super(HotSwapClassLoader.class.getClassLoader());
    }
    public Class loadByte(byte[] classByte){
        return defineClass(null,classByte,0,classByte.length);
    }
}
