package com.hyj.collection;

import java.util.Objects;

public class hhh {

    private int a;

    public hhh() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        hhh hhh = (hhh) o;
        return a == hhh.a;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a);
    }
}
