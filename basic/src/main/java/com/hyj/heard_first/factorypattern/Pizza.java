package com.hyj.heard_first.factorypattern;

import java.util.ArrayList;
import java.util.List;

public abstract class Pizza {

     String name;
     Dough dough;
     Sauce sauce;
     List<String> toppings = new ArrayList<>();

    public abstract void prepare();
    /*{
        System.out.println("preparing " + name);
        System.out.println("Tossing dough... ");
        System.out.println("Adding sauce.. ");
        System.out.println("Adding toppings: ");
        for(String item:toppings){
            System.out.println("  " + item);
        }
    }*/

    public void bake(){
        System.out.println("pizza bake");
    }

    public void cut(){
        System.out.println("pizza cut");
    }

    public void box(){
        System.out.println("pizza bake");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
