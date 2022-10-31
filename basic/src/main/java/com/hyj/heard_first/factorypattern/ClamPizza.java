package com.hyj.heard_first.factorypattern;

public class ClamPizza extends Pizza {
    PizzaIngredientFactory factory;

    public ClamPizza(PizzaIngredientFactory factory) {
        this.factory = factory;
    }

    @Override
    public void prepare() {
        System.out.println("preparing " + name);
        dough = factory.createDough();
        sauce = factory.createSauce();
    }
}
