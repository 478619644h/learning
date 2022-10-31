package com.hyj.heard_first.factorypattern;

public class NYStylePizzaStore extends PizzaStore {



    @Override
    public Pizza createPizza(String type) {
        Pizza pizza = null;
        PizzaIngredientFactory factory = new NYPizzaIngredientFactory();
        if(type.equals("cheese")){
            pizza =  new CheesePizza(factory);
            pizza.setName("new york style cheese pizza");
        } else if(type.equals("clam")){
            pizza = new ClamPizza(factory);
            pizza.setName("new york style clam pizza");
        }

        return pizza;
    }


}
