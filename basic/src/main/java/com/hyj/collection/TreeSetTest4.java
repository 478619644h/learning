package com.hyj.collection;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * 定制排序
 *
 * TreeSet的自然排序是根据集合元素的大小，TreeSet将它们以升序排序。如果我们需要实现定制排序，则可以通过Comparator接口的帮助(类似PHP中
 * 的array_map回调处理函数的思想)。该接口里包含一个int compare(T o1， T o2)方法，该方法用于比较大小
 *
 * 1) equals、compareTo决定的是怎么比的问题，即用什么field进行大小比较
 * 2) 自然排序、定制排序、Comparator决定的是谁大的问题，即按什么顺序(升序、降序)进行排序
 * 它们的关注点是不同的，一定要注意区分
 */
public class TreeSetTest4 {

    public static void main(String[] args)
    {
        TreeSet ts = new TreeSet(new Comparator()
        {
            //根据M对象的age属性来决定大小
            public int compare(Object o1, Object o2)
            {
                M m1 = (M)o1;
                M m2 = (M)o2;
                return m1.age > m2.age ? -1
                        : m1.age < m2.age ? 1 : 0;
            }
        });
        ts.add(new M(5));
        ts.add(new M(-3));
        ts.add(new M(9));
        System.out.println(ts);
    }
}
class M
{
    int age;
    public M(int age)
    {
        this.age = age;
    }
    public String toString()
    {
        return "M[age:" + age + "]";
    }
}