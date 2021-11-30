package com.hyj.juc.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchonesdQueue {

    public static void main(String[] args) {
        SynchronousQueue queue =  new SynchronousQueue();
        new Thread(()->{

                try {
                    System.out.println("add a");
                    queue.put("a");
                    System.out.println("add b");
                    queue.put("b");
                    System.out.println("add c");
                    queue.put("c");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("poll a");
                System.out.println(queue.poll());

                TimeUnit.SECONDS.sleep(2);
                System.out.println("poll b");
                System.out.println(queue.poll());

                TimeUnit.SECONDS.sleep(2);
                System.out.println("poll c");
                System.out.println(queue.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
