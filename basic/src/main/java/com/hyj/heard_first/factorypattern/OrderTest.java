package com.hyj.heard_first.factorypattern;

public class OrderTest {

    public static void main(String[] args) {
        PizzaStore pizzaStore = new NYStylePizzaStore();
        Pizza pizza = pizzaStore.orderPizza("cheese");
        System.out.println(pizza.getName());
    }

}
