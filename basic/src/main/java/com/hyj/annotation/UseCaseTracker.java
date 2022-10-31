package com.hyj.annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UseCaseTracker {

    public static void trackUseCase(List<Integer> useCases,Class<?> cl){
        for (Method m:cl.getDeclaredMethods()){
            UseCase uc = m.getAnnotation(UseCase.class);
            if(uc != null){
                System.out.println("Found use case:" + uc.id() + " " + uc.description());
                useCases.remove(Integer.valueOf(uc.id()));
            }
        }
        for (int i:useCases){
            System.out.println("waring : missing use case- " + i);
        }
    }

    public static void main(String[] args) {
        List<Integer> useCases = new ArrayList<>();
        Collections.addAll(useCases,47,48,49,50);
        trackUseCase(useCases,PWUtil.class);
    }

}
