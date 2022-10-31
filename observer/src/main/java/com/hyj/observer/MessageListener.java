package com.hyj.observer;

import com.hyj.observer.MessageEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener implements ApplicationListener<MessageEvent> {
    @Override
    public void onApplicationEvent(MessageEvent messageEvent) {
        System.out.println("用户注册成功，执行监听事件"+messageEvent.getSource());
    }
}
