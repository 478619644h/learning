package com.hyj.heard_first.factorypattern;

public class CheesePizza extends Pizza {
        PizzaIngredientFactory factory;

    public CheesePizza(PizzaIngredientFactory factory) {
        this.factory = factory;
    }

    @Override
    public void prepare() {
        System.out.println("preparing " + name);
        dough = factory.createDough();
        sauce = factory.createSauce();
    }
}
