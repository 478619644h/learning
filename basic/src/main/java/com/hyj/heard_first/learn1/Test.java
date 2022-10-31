package com.hyj.heard_first.learn1;

public class Test {

    public static void main(String[] args) {
       Character character = new Queen();
       character.setWeaponBehavior(new KnifeBehavior());
       character.fight();
    }

}
