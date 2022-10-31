package com.hyj.observer;

import org.springframework.context.ApplicationEvent;

public class MessageEvent extends ApplicationEvent {

    public MessageEvent(Object source) {
        super(source);
    }
}
