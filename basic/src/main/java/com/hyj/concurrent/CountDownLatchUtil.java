package com.hyj.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchUtil {

    private int pollSize = 10;
    private CountDownLatch start;
    private CountDownLatch end;

    public CountDownLatchUtil() {
        this(10);
    }

    public CountDownLatchUtil(int pollSize){
        this.pollSize = pollSize;
        start = new CountDownLatch(1);
        end = new CountDownLatch(pollSize);
    }

    //并发方法 同时执行pollSize个线程
    public void latch(MyfunctionInterface myfunctionInterface) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(pollSize);
        for(int i = 0;i<pollSize;i++){
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        start.await();//等待就绪 count=0时开始执行
                        myfunctionInterface.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        end.countDown();//count-1  为0时开始执行
                    }
                }
            };
            executorService.submit(run);
        }
        start.countDown();
        end.await();
        executorService.shutdown();
    }
}




