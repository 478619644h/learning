package com.hyj.observer.iobserver.event;

import org.springframework.context.ApplicationEvent;

public class BaseEvent extends ApplicationEvent {


    public BaseEvent() {
        this("");
    }

    public BaseEvent(Object source) {
        super(source);
    }
}
