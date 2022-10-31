package com.hyj.observer.iobserver.listener;

import com.hyj.observer.iobserver.event.RegisterMessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(2)
public class RegisterMessageListenerImpl implements IEventListener<RegisterMessageEvent> {

    @Override
    public void handler(RegisterMessageEvent event) {
        log.info("用户注册成功register 2，执行监听事件:{}",event.getMsgId());
        throw new RuntimeException("hhahahahhah");
    }


    @Override
    public void handleException(Throwable exception) {
        log.error(exception.getMessage());
    }
}
