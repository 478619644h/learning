package com.hyj.map;

import java.util.EnumMap;

/**
 * 与创建普通Map有所区别的是，创建EnumMap是必须指定一个枚举类，从而将该EnumMap和指定枚举类关联起来
 */
public class EnumMapTest {
    public static void main(String[] args) {
        //创建一个EnumMap对象，该EnumMap的所有key
        //必须是Season枚举类的枚举值
        EnumMap enumMap = new EnumMap(Season.class);
        enumMap.put(Season.SUMMER, "夏日炎炎");
        enumMap.put(Season.SPRING, "春暖花开");
        System.out.println(enumMap);
    }
}

enum Season {
    SPRING, SUMMER, FALL, WINTER
}