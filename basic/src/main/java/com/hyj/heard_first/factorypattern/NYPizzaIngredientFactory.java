package com.hyj.heard_first.factorypattern;

public class NYPizzaIngredientFactory implements PizzaIngredientFactory {
    @Override
    public Dough createDough() {
        return new ThinCrustDough();
    }

    @Override
    public Sauce createSauce() {
        return new MarinaraSauce();
    }

    @Override
    public Cheese createCheese() {
        return new ReffianoCheese();
    }

    @Override
    public Veggies[] createVeggies() {
        Veggies veggies[] = {new Garlic()};
        return veggies;
    }

    @Override
    public Pepperoni createPepperoni() {
        return new SlicedPepperoni();
    }

    @Override
    public Clams createClams() {
        return new FreshClams();
    }
}


class ThinCrustDough extends Dough{}
class MarinaraSauce extends Sauce{}
class ReffianoCheese extends Cheese{}
class Garlic extends Veggies{}
class SlicedPepperoni extends Pepperoni{}
class FreshClams extends Clams{}