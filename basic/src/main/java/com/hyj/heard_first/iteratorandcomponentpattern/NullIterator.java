package com.hyj.heard_first.iteratorandcomponentpattern;

import java.util.Iterator;
import java.util.function.Consumer;

public class NullIterator implements Iterator {

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return null;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

}
