package com.hyj.classloaderdemo;

import java.lang.reflect.Method;

/**
 * JavaClass执行工具
 */
public class JavaClassExecuter {


    /**
     * 执行外部传过来的代表java类的byte数组
     * 将输入的byte数组中代表java.lang.System的CONSTANT_Utf8_info常量修改为劫持后的HackSystem类
     * 执行方法为该类型的static main（String[] args）方法，输出结果为该类向System.out/err输出的信息
     * @param classByte 代表一个java类的byte数组
     * @return 执行结果
     */
    public static String execute(byte[] classByte){
        HackSystem.clearBuffer();
        ClassModifier cm = new ClassModifier(classByte);
        byte[] modiBytes = cm.modifyUTF8Constant("java/lang/System","com/hyj/classloaderdemo/HackSystem");
        HotSwapClassLoader loder = new HotSwapClassLoader();
        Class clazz = loder.loadByte(modiBytes);
        try {
            Method method = clazz.getMethod("main",new Class[]{String[].class});
            method.invoke(null,new String[]{null});
        } catch (Throwable e) {
            e.printStackTrace(HackSystem.out);
        }
        return HackSystem.getBufferString();
    }

}
