package com.hyj.heard_first.factorypattern;

public interface PizzaIngredientFactory {

    Dough createDough();
    Sauce createSauce();
    Cheese createCheese();
    Veggies[] createVeggies();
    Pepperoni createPepperoni();
    Clams createClams();


}

class Dough{}
class Sauce{}
class Cheese{}
class Veggies{}
class Pepperoni{}
class Clams{}