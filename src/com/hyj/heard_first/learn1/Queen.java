package com.hyj.heard_first.learn1;

public class Queen extends Character {
    @Override
    public void fight() {
        System.out.println("queen");
        this.weaponBehavior.useWeapon();
    }
}
