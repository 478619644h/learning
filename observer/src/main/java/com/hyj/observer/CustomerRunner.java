package com.hyj.observer;

import com.hyj.observer.iobserver.event.RegisterMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CustomerRunner implements ApplicationRunner {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private EventPublish eventPublish;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //applicationEventPublisher.publishEvent(new MessageEvent("777"));
        eventPublish.asyncPublish(RegisterMessageEvent.builder().msgId("1234567890").build());
        abc.A.getStr();

    }

    static enum abc{
        A{
            private final static String a = "12";

            public String getStr(){
                return a;
            }
        };

        public abstract String getStr();
    }
}
