package com.hyj.heard_first.iteratorandcomponentpattern;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Menu extends MenuComponent {

    List<MenuComponent> menuComponets = new ArrayList<>();

    String name;

    String description;

    public Menu(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public void add(MenuComponent menuComponent) {
        menuComponets.add(menuComponent);
    }

    @Override
    public void remove(MenuComponent menuComponent) {
        menuComponets.remove(menuComponent);
    }

    @Override
    public MenuComponent getChild(int i) {
        return menuComponets.get(i);
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
        System.out.print("\n" + getName());
        System.out.println(", " + getDescription());
        System.out.println("---------------");

        Iterator iterator = menuComponets.iterator();
        while (iterator.hasNext()){
            MenuComponent menuComponent = (MenuComponent) iterator.next();
            menuComponent.print();
        }
    }

    @Override
    Iterator createIterator() {
        return new CompositeIterator(menuComponets.iterator());
    }
}
