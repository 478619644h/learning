package com.hyj.collection;

import java.util.LinkedHashSet;

/**
 * 元素的顺序总是与添加顺序一致，同时要明白的是，LinkedHashSetTest是HashSet的子类，因此它不允许集合元素重复
 */
public class LinkedHashSetTest {

    public static void main(String[] args)
    {
        LinkedHashSet books = new LinkedHashSet();
        books.add("Java");
        books.add("LittleHann");
        System.out.println(books);

        //删除 Java
        books.remove("Java");
        //重新添加 Java
        books.add("Java");
        System.out.println(books);
    }

}
