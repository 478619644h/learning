package com.hyj.thread.completion;


import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


/**
 * CompletionStage 接口如何描述串行关系、AND 聚合关系、OR 聚合关系以及异常处理。
 *
 */
public class CompletionStageL {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //or();
        //exceptionHandle();
        serialize();
    }


    /**
     * 串行关系
     *
     * CompletionStage 接口里面描述串行关系，主要是 thenApply、thenAccept、thenRun 和 thenCompose 这四个系列的接口。
     *  thenApply 系列函数里参数 fn 的类型是接口 Function，这个接口里与 CompletionStage 相关的方法是 R apply(T t)，
     *  这个方法既能接收参数也支持返回值，所以 thenApply 系列方法返回的是CompletionStage。
     *
     *  thenAccept 系列方法里参数 consumer 的类型是接口Consumer，这个接口里与 CompletionStage 相关的方法是 void accept(T t)，
     *  这个方法虽然支持参数，但却不支持回值，所以 thenAccept 系列方法返回的是CompletionStage。
     *
     *  thenRun 系列方法里 action 的参数是 Runnable，所以 action 既不能接收参数也不支持返回值，
     *  所以 thenRun 系列方法返回的也是CompletionStage。
     *
     *  Async 代表的是异步执行 fn、consumer 或者 action。其中，需要你注意的是 thenCompose 系列方法，
     *  这个系列的方法会新创建出一个子流程，最终结果和 thenApply 系列是相同的。
     *
     */
    static void serialize() throws ExecutionException, InterruptedException {
        CompletableFuture<String> f0 =
                CompletableFuture
                        .supplyAsync( () -> {
                            System.out.println(Thread.currentThread().getName());
                            return  "Hello World";
                        })      //①
                        .thenApply(s -> s + " QQ")  //②
                        .thenApply(String::toUpperCase);//③

        System.out.println(f0.get());
    }

    /**
     * AND 汇聚关系
     * CompletionStage 接口里面描述 AND 汇聚关系，主要是 thenCombine、thenAcceptBoth 和 runAfterBoth 系列的接口，
     * 这些接口的区别也是源自 fn、consumer、action 这三个核心参数不同。它们的使用你可以参考烧水泡茶的实现程序。
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    static void and() throws ExecutionException, InterruptedException {
        CompletableFuture f1 = CompletableFuture.runAsync(()->{
            System.out.println(1);
        });

        CompletableFuture f2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(2);
            return 2;
        });

        CompletableFuture f3 = f1.thenCombine(f2,(f,__) -> {
            System.out.println(3);
            System.out.println("f -> " + __);
            return 3;
        });

        System.out.println(f3.get());

    }


    /**
     * OR 汇聚关系
     *
     * CompletionStage 接口里面描述 OR 汇聚关系，主要是 applyToEither、acceptEither 和 runAfterEither 系列的接口，
     * 这些接口的区别也是源自 fn、consumer、action 这三个核心参数不同。
     *
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    static void or() throws ExecutionException, InterruptedException {

        CompletableFuture<String> f1 =
                CompletableFuture.supplyAsync(()->{
                    int t = getRandom(5, 10);
                    try {
                        TimeUnit.SECONDS.sleep(t);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return String.valueOf(t);
                });

        CompletableFuture<String> f2 =
                CompletableFuture.supplyAsync(()->{
                    int t = getRandom(5, 10);
                    try {
                        TimeUnit.SECONDS.sleep(t);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return String.valueOf(t);
                });

        CompletableFuture<String> f3 =
                f1.applyToEither(f2,s -> s);

        System.out.println(f3.get());

    }


    /**
     * 异常处理
     *
     * 虽然上面我们提到的 fn、consumer、action 它们的核心方法都不允许抛出可检查异常，但是却无法限制它们抛出运行时异常，
     * 例如执行 7/0 就会出现除零错误这个运行时异常。非异步编程里面，我们可以使用 try{}catch{}来捕获并处理异常，
     * 那在异步编程里面，异常该如何处理呢？
     * CompletionStage 接口给我们提供的方案非常简单，比 try{}catch{}还要简单，下面是相关的方法，
     * 使用这些方法进行异常处理和串行操作是一样的，都支持链式编程方式。
     *
     *
     * CompletionStage exceptionally(fn);
     * CompletionStage<R> whenComplete(consumer);
     * CompletionStage<R> whenCompleteAsync(consumer);
     * CompletionStage<R> handle(fn);
     * CompletionStage<R> handleAsync(fn);
     *
     * exceptionally() 的使用非常类似于 try{}catch{}中的 catch{}，但是由于支持链式编程方式，所以相对更简单。
     * 既然有 try{}catch{}，那就一定还有 try{}finally{}，
     * whenComplete() 和 handle() 系列方法就类似于 try{}finally{}中的 finally{}，
     * 无论是否发生异常都会执行 whenComplete() 中的回调函数 consumer 和 handle() 中的回调函数 fn。
     * whenComplete() 和 handle() 的区别在于 whenComplete() 不支持返回结果，而 handle() 是支持返回结果的。
     */
    static void  exceptionHandle(){

        CompletableFuture<Integer> f0 = CompletableFuture
                                .supplyAsync(()->(7/0))
                                .thenApply(r->r*10)
                                .exceptionally(e->{
                                    e.printStackTrace();
                                    return 0;
                                });
        System.out.println(f0.join());

    }

    static int getRandom(int origin,int bound){
        Random random = new Random();
        if (origin < bound) {
            int n = bound - origin;
            if (n > 0) {
                return random.nextInt(n) + origin;
            }
            else {  // range not representable as int
                int r;
                do {
                    r = random.nextInt();
                } while (r < origin || r >= bound);
                return r;
            }
        }
        else {
            return random.nextInt();
        }
    }

}
