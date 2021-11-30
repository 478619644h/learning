package com.hyj.juc.function;


import java.util.function.Predicate;

@FunctionalInterface
public interface PridicateStr {

    boolean ifNull();

    default boolean isEmpty(String a){
        return  a.isEmpty();
    }

}
