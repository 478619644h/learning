package com.hyj.invoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class TestMethodHandle {

    static class ClassA{
        public void pringtln(String s){
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws Throwable {
        Object obj = System.currentTimeMillis() % 2 == 0?System.out:new ClassA();
        getPrintMH(obj).invokeExact("ssssss");
    }

    private static MethodHandle getPrintMH(Object reveiver) throws NoSuchMethodException, IllegalAccessException {
        MethodType mt = MethodType.methodType(void.class,String.class);
        return MethodHandles.lookup().findVirtual(reveiver.getClass(),"pringtln",mt).bindTo(reveiver);
    }
}
