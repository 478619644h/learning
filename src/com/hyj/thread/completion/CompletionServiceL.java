package com.hyj.thread.completion;

import java.util.concurrent.*;

public class CompletionServiceL {


    static ExecutorService executors = Executors.newFixedThreadPool(3);

    static CompletionService<Integer> cs = new ExecutorCompletionService(executors);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(t());
    }

    static int t() throws InterruptedException, ExecutionException {
        cs.submit(() -> getPriceByS1());
        cs.submit(() -> getPriceByS2());
        cs.submit(() -> getPriceByS3());
        /*Future<Integer> future = cs.poll();
        while (future != null){
            Integer price = future.get();
            executors.execute(() -> save(price));
            future  = cs.poll();
        }*/

        for (int i = 0; i < 3; i++) {
            Integer price = cs.take().get();
            executors.execute(() -> save(price));
        }
        //executors.shutdown();
        //需要全部任务结束的结果要做 线程控制等待结果在返回因为 上述executors.execute是异步执行
        return 1;
    }


    static int getPriceByS1() throws InterruptedException {
        TimeUnit.SECONDS.sleep(CompletionStageL.getRandom(1, 5));
        return 1;
    }

    static int getPriceByS2() throws InterruptedException {
        TimeUnit.SECONDS.sleep(CompletionStageL.getRandom(1, 5));
        return 2;
    }

    static int getPriceByS3() throws InterruptedException {
        TimeUnit.SECONDS.sleep(CompletionStageL.getRandom(1, 5));
        return 3;
    }

    static void save(int i) {
        //TimeUnit.SECONDS.sleep(6);
        System.out.println("save getPriceBy" + i);
    }

}
