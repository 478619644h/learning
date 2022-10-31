package com.hyj.memory;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * vm args -XX:PermSize=10M -XX:MaxPermSize=10M  //1.7以前可以用来设置永久带的大小
 * 1.8可以用-XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m
 *
 */

public class RuntiemConstantPoolOOm {

    public static void main(String[] args) {
        //使用list保持常量池引用，避免full gc 回收常量池行为
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true){
            list.add(new String(String.valueOf(i++)).intern());
        }

    }



}
