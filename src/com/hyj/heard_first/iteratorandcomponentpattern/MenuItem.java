package com.hyj.heard_first.iteratorandcomponentpattern;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MenuItem extends MenuComponent {


    String name;

    String description;

    boolean vegetarain;

    double price;

    public MenuItem(String name, String description, boolean vegetarain, double price) {
        this.name = name;
        this.description = description;
        this.vegetarain = vegetarain;
        this.price = price;
    }

    public boolean isVegetarain() {
        return vegetarain;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void print() {
        System.out.print(" " + getName());
        if (isVegetarain()) {
            System.out.print("(v)");
        }
        System.out.println(", " + getPrice());
        System.out.println("        -- " + getDescription());


    }

    @Override
    Iterator createIterator() {
        return new NullIterator();
    }
}
