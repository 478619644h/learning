package com.hyj.algorithm;

public class test {

    static Cat cat = new Cat("ss");

    public static void main(String[] args) {
        System.out.println(cat.getS());
        ss(cat);
        System.out.println(cat.getS());
    }

    static Cat ss(Cat cat){
        cat.setS("wqwqw");
        return cat;
    }


}
