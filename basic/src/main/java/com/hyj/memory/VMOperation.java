package com.hyj.memory;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class VMOperation {

    //线程死循环
    public static void createBusyThread(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                    ;
            }
        },"createByusThread");
        thread.start();
    }

    public static void createLockThread(final Object lock){
        Thread thread= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (lock){
                        lock.wait();
                    }
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"createLockThread");
        thread.start();
    }

    static class SynAddRunable implements Runnable{

        int a,b;

        public SynAddRunable(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            synchronized (Integer.valueOf(a)){
                synchronized (Integer.valueOf(b)){
                    System.out.println(a+b);
                }
            }
        }
    }


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();
        Object o = new Object();
        createLockThread(o);
        br.readLine();
        createBusyThread();

       /*for(int i = 0;i<200;i++){
           new Thread(new SynAddRunable(1,2)).start();
           new Thread(new SynAddRunable(2,1)).start();
       }*/

    }

}
