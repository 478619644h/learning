package com.hyj;

import java.util.HashMap;

public class TryCatch {
    private static int x = 0;

    public static void main(String[] args) {
        System.out.println(String.valueOf(ff()));
    }

    public static int ff(){
        int i = 0;
        try {
            throw new Exception("test");
        }catch (Exception e){
            System.out.println("捕获到异常");
            return i;
        }finally {
            i++;
            System.out.println("finale execute");
            return i;
        }
    }

    class myMap extends HashMap implements Comparable{

        @Override
        public int compareTo(Object o) {
             return 0;
        }
    }


}
