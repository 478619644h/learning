package com.hyj.observer.iobserver.listener;

import com.hyj.observer.iobserver.event.BaseEvent;
import org.springframework.context.ApplicationListener;

public interface IEventListener<T extends BaseEvent> extends ApplicationListener<T> {

    /**
     * 观察者的业务逻辑处理
     * @param event
     */
    default void onApplicationEvent(T event){
        try {
            if (support(event)) {
                handler(event);
            }
        } catch (Throwable e) {
            /**
             *
             */
            handleException(e);
        }
    }

    /**
     * 默认执行观察者的逻辑的
     * @param event
     * @return
     */
    default boolean support(T event) {
        return true;
    }

    /**
     *  观察者的逻辑，交给不同子类自定义实现
     * @param event
     */
    void handler(T event);

    /**
     * 异常默认不处理
     * @param exception
     */
    default void handleException(Throwable exception) {
    }

}
