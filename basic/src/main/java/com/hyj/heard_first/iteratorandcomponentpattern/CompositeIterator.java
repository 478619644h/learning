package com.hyj.heard_first.iteratorandcomponentpattern;

import java.util.Iterator;
import java.util.Stack;

public class CompositeIterator implements Iterator<MenuComponent> {

    Stack stack = new Stack();

    public CompositeIterator(Iterator iterator) {
        this.stack.push(iterator);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }


    @Override
    public boolean hasNext() {
        if(stack.isEmpty()){
            return false;
        } else {
            Iterator iterator = (Iterator) stack.peek();
            if (!iterator.hasNext()) {
                stack.pop();
                return hasNext();
            } else {
                return true;
            }

        }
    }

    @Override
    public MenuComponent next() {
        if (hasNext()) {
            Iterator iterator = (Iterator) stack.peek();
            MenuComponent component = (MenuComponent) iterator.next();
            if(component instanceof Menu){
                stack.push(component.createIterator());
            }
            return component;
        } else {
            return null;
        }
    }
}
