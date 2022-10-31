package com.hyj.heard_first.factorypattern;

public class SimplePizzaFactory {

    public Pizza createPizza(String type){
        Pizza pizza = null;
        if(type.equals("cheese")){
            //pizza = new CheesePizza();
        }
        return pizza;
    }
}
