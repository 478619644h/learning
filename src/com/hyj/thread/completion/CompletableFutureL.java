package com.hyj.thread.completion;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 使用默认线程池 ForkJoinPool
 static CompletableFuture<Void> runAsync(Runnable runnable)
 static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
 可以指定线程池
 static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor)
 static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)
 */
public class CompletableFutureL {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture f1 = CompletableFuture.runAsync(()->{
            System.out.println(1);
        });

        CompletableFuture f2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(2);
            return 2;
        });

        CompletableFuture f3 = f1.thenCombine(f2,(f,__) -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(3);
            System.out.println("f -> " + __);
            return 3;
        });

        System.out.println(f3.get());

    }
}
