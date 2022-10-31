package com.hyj.heard_first.guarded_suspension;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TEST {

    static Handle handle = new Handle();

    static Random random = new Random();

    static int n = 100;

    static ExecutorService customerPool = Executors.newFixedThreadPool(10,
            new BasicThreadFactory.Builder().namingPattern("customrt-pool-%d").build());

    static ExecutorService providerPool = Executors.newFixedThreadPool(10,
            new BasicThreadFactory.Builder().namingPattern("provider-pool-%d").build());

    public static void main(String[] args) {
        int a = 60;
        System.out.println(192 & 0x3F);

        /*for (int i = 0;i < n; i++){
            providerPool.submit(() ->{
                handle.handleWebReq();
                try {
                    TimeUnit.SECONDS.sleep(random.nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        for (int i = 0;i < n; i++){
            customerPool.submit(() ->{
                MessageQueue.onMessage(message -> GuardedObject.fireEvent( message.id, message));
                try {
                    TimeUnit.SECONDS.sleep(random.nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }*/

    }


}

class Handle {

     void  handleWebReq() {

        int id = NoGenerater.getNo();
        //创建一消息
        Message msg1 = new Message(id, "{...}");
        //创建GuardedObject实例
        GuardedObject go = GuardedObject.create(id);
        //发送消息
        MessageQueue.send(msg1);
        //等待MQ消息
        Message r = (Message) go.get(t -> t != null);
        System.out.println(id + " 号请求 currentThread: " + Thread.currentThread().getName() + " 消息: " + msg1 + " 送至MQ等待响应 " + "响应返回：" + r);
    }


}

class Message {

    Integer id;
    String content;

    public Message(Integer id, String content) {
        this.id = id;
        this.content = content;
    }


    public Integer getId() {
        return id;
    }

    public Message setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Message setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Message{");
        sb.append("id=").append(id);
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

class NoGenerater {

   final static AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);

   static int getNo(){
       return ATOMIC_INTEGER.getAndIncrement();
   }

}

