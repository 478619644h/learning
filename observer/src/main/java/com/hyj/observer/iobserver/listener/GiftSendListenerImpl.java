package com.hyj.observer.iobserver.listener;

import com.hyj.observer.iobserver.event.GiftSendEvent;
import com.hyj.observer.iobserver.event.RegisterMessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GiftSendListenerImpl implements IEventListener<GiftSendEvent> {

    @Override
    public void handler(GiftSendEvent event) {
        log.info("用户注册成功GiftSendEvent，执行监听事件:{}",event.getGifIt());
        throw new RuntimeException("hhahahahhah");
    }


    @Override
    public void handleException(Throwable exception) {
        log.error(exception.getMessage());
    }
}
