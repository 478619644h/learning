package com.hyj.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierUtil {

    private int pollSize = 10;
    private CyclicBarrier cyclicBarrier;
    ExecutorService executorService;

    public CyclicBarrierUtil(int pollSize) {
        this.pollSize = pollSize;
        executorService = Executors.newFixedThreadPool(pollSize);
        cyclicBarrier = new CyclicBarrier(pollSize,()->{
            executorService.shutdown();
        });

    }

    public void barrier(MyfunctionInterface myfunctionInterface){
        for(int i =0;i<pollSize;i++){
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        cyclicBarrier.await();
                        myfunctionInterface.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            };
            executorService.submit(run);
        }
    }
}
