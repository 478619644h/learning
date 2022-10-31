package com.hyj.juc.function;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public class Test {


    static Function<String,Object> function = (s) -> {return "aa";};

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Constructor<Test> constructor = Test.class.getConstructor(null);
        constructor.newInstance();

        PridicateStr pridicateStr = () -> {return 1==1; };
        System.out.println(function.apply(""));
    }
}
