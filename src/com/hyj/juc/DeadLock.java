package com.hyj.juc;

import java.util.concurrent.TimeUnit;

public class DeadLock {
    public static void main(String[] args) {

        String lock1 = "locak1";

        String lock2 = "lock2";

        new Thread(new myLoad(lock1,lock2),"T1").start();

        new Thread(new myLoad(lock2,lock1),"T2").start();

    }

}


class myLoad implements Runnable{

    private String lock1;

    private String lock2;

    public myLoad(String lock1, String lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    @Override
    public void run() {
        synchronized (lock1){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "=>" + lock1);
            synchronized (lock2){

            }
        }
    }
}
