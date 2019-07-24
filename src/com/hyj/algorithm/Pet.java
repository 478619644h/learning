package com.hyj.algorithm;

public class Pet {

    private String type;

    public Pet(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "this is a " + type;
    }
}
