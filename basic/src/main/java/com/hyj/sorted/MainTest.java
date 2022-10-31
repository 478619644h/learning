package com.hyj.sorted;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class MainTest {

    static int[] a = {3,1,4,6,23,6,72,9};

    public static void main(String[] args) {
        //test bubble sort
        int[] sortedArr = test(new BubbleSort());
        System.out.println(Arrays.toString(sortedArr));

        System.out.println("==============select sort================");
        System.out.println(Arrays.toString(test(new SelectSort())));

        System.out.println("==============insert sort================");
        System.out.println(Arrays.toString(test(new Insertsort2())));

        System.out.println("==============shell sort================");
        System.out.println(Arrays.toString(test(new ShellSort())));

        System.out.println("==============MergeSort sort================");
        System.out.println(Arrays.toString(test(new MergeSort())));

        System.out.println("==============QuickSort sort================");
        System.out.println(Arrays.toString(test(new QuickSort())));

        System.out.println("==============HeapSort sort================");
        System.out.println(Arrays.toString(test(new HeapSort())));

        System.out.println("==============CountingSort sort================");
        System.out.println(Arrays.toString(test(new CountingSort())));
    }

    public static int[] test(IArraySort arraySort){
        IArraySort proxyObj =(IArraySort)new DynamicProxy().bind(arraySort);
       return proxyObj.sort(a);
    }

    static class DynamicProxy implements InvocationHandler {

        Object obj;

        Object bind(Object obj){
            this.obj = obj;
            return Proxy.newProxyInstance(obj.getClass().getClassLoader(),obj.getClass().getInterfaces(),this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            long start = System.nanoTime();
            Object result = method.invoke(obj,args);
            long end = System.nanoTime();
            System.out.println(obj.getClass().getSimpleName() + " 用时 " + (end - start) + "纳秒");
            return result;
        }
    }

}
