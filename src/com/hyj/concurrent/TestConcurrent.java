package com.hyj.concurrent;

import java.util.concurrent.Semaphore;

public class TestConcurrent {


    public static void main(String[] args)  {
        //testCyclicBarrier();
        testSemaphore();
    }

    public static void testCountDownLatch() throws InterruptedException{
        long s = 10;
        CountDownLatchUtil countDownLatchUtil = new CountDownLatchUtil(10);
        countDownLatchUtil.latch(()->{
            System.out.println(System.currentTimeMillis()-s);
        });
    }

    public static void testCyclicBarrier(){
        long s = 10;
        CyclicBarrierUtil cyclicBarrierUtil = new CyclicBarrierUtil(10);
        cyclicBarrierUtil.barrier(()->{
            System.out.println(System.currentTimeMillis()-s);
        });
    }

    public static void testSemaphore(){
        int n = 8;//工人
        Semaphore semaphore = new Semaphore(5);//同时可以有n个线程可以获得许可
        for(int i = 0;i < n;i++){
            new Worker(i,semaphore).start();
        }
    }

    static class Worker extends Thread{
        private int num;
        private Semaphore semaphore;
        public Worker(int num,Semaphore semaphore){
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();//获取证书
                System.out.println("worker " + this.num + "占用一个机器在生产");
                Thread.sleep(2000);
                System.out.println("worker " + this.num + "释放出机器");
                semaphore.release();//释放证书
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
