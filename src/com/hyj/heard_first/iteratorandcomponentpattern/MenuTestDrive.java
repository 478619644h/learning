package com.hyj.heard_first.iteratorandcomponentpattern;

import java.util.Random;

public class MenuTestDrive {

    public static void main(String[] args) {


         Random random = new Random(System.currentTimeMillis());
         random.nextInt(10);

         MenuComponent pancakeMenu = new Menu("PANCAKE HOUSE MENU","Breakfast");
         MenuComponent dinerMenu = new Menu("DINER MENU","Lunch");
         MenuComponent cafeMenu = new Menu("CAFA MENU","Dinner");
         MenuComponent dessertMenu = new Menu("DESSERT MENU","Dessert of course!");

         MenuComponent allMenu = new Menu("ALL MENUS","All menus combined");
         allMenu.add(pancakeMenu);
         allMenu.add(dinerMenu);
         allMenu.add(cafeMenu);


         dinerMenu.add(new MenuItem("Pasta","Spaghetti with marinara Sauce,and a slice of sourdough bread",
                 true,3.89));

         dinerMenu.add(dessertMenu);

         dessertMenu.add(new MenuItem("Apple pie",
                 "Apple pie with a flakey crust,topped with vanilla ice cream",
                 true,1.59));

         Waitress waitress = new Waitress(allMenu);

         waitress.printMenu();

    }
}
