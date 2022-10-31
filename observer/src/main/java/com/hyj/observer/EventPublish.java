package com.hyj.observer;

import com.hyj.observer.iobserver.event.BaseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class EventPublish {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ThreadPoolTaskExecutor eventExecutor;

    //同步阻塞
    public void publish(BaseEvent event) {
        applicationContext.publishEvent(event);
    }

    //异步发布（异步非阻塞）
    public void asyncPublish(BaseEvent event) {
        eventExecutor.execute(()->{
            publish(event);
        });
    }
}
