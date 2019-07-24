package com.hyj.collection;

import java.util.TreeSet;

/**
 * 与HashSet集合采用hash算法来决定元素的存储位置不同，TreeSet采用红黑树的数据结构来存储集合元素。TreeSet支持两种排序方式: 自然排序、
 * 定制排序
 *
 * 1. 自然排序:
 *
 * TreeSet会调用集合元素的compareTo(Object obj)方法来比较元素之间的大小关系，然后将集合元素按升序排序，即自然排序。如果试图把一个对象
 * 添加到TreeSet时，则该对象的类必须实现Comparable接口，否则程序会抛出异常。
 *
 * 当把一个对象加入TreeSet集合中时，TreeSet会调用该对象的compareTo(Object obj)方法与容器中的其他对象比较大小，然后根据红黑树结构找到
 * 它的存储位置。如果两个对象通过compareTo(Object obj)方法比较相等，新对象将无法添加到TreeSet集合中(牢记Set是不允许重复的概念)。
 *
 * 注意: 当需要把一个对象放入TreeSet中，重写该对象对应类的equals()方法时，应该保证该方法与compareTo(Object obj)方法有一致的结果，即
 * 如果两个对象通过equals()方法比较返回true时，这两个对象通过compareTo(Object obj)方法比较结果应该也为0(即相等)
 *
 * 看到这里，我们应该明白：
 *
 * 1) 对与Set来说，它定义了equals()为唯一性判断的标准，而对于到了具体的实现，HashSet、TreeSet来说，它们又会有自己特有的唯一性判断标准，
 * 只有同时满足了才能判定为唯一性
 * 2) 我们在操作这些集合类的时候，对和唯一性判断有关的函数重写要重点关注
 */
public class TreeSetTest {

    public static void main(String[] args) {
        TreeSet nums = new TreeSet();
        //向TreeSet中添加四个Integer对象
        nums.add(5);
        nums.add(2);
        nums.add(10);
        nums.add(-9);

        //输出集合元素，看到集合元素已经处于排序状态
        System.out.println(nums);

        //输出集合里的第一个元素
        System.out.println(nums.first());

        //输出集合里的最后一个元素
        System.out.println(nums.last());

        //返回小于4的子集，不包含4
        System.out.println(nums.headSet(4));

        //返回大于5的子集，如果Set中包含5，子集中还包含5
        System.out.println(nums.tailSet(5));

        //返回大于等于-3，小于4的子集。
        System.out.println(nums.subSet(-3, 4));
    }

}
