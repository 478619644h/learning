package com.hyj.heard_first.guarded_suspension;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class MessageQueue {

    final static BlockingQueue<Message> MQ = new ArrayBlockingQueue<>(10);

    static void send(Message message){
        try {
            MQ.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void onMessage(Consumer<Message> consumer){
        while (true){
            try {
                Message message = MQ.take();
                Message msg = new Message(message.getId(),"{+++} current handle thread : " + Thread.currentThread().getName());
                consumer.accept(msg);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
