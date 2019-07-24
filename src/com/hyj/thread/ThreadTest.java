package com.hyj.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadTest {

    private static AtomicInteger integer = new AtomicInteger();

    public static void increase(){
        integer.incrementAndGet();
    }

    public static final int THREADS_COUNT = 20;

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREADS_COUNT];
        for(Thread t:threads){
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0;i < 10000;i++){
                        increase();
                    }
                }
            });
            t.start();
        }
        while (Thread.activeCount() > 2){
            System.out.println("ss");
            Thread.yield();
        }

        System.out.println(integer);
    }

}
