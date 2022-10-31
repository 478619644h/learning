package basic.src.main.java.com.hyj.queue;

import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.*;

public class ProviderConsumerTest {

     ExecutorService provider = Executors.newFixedThreadPool(2,new DefaultThreadFactory("PROVIDER"));
    static ExecutorService consumer = Executors.newFixedThreadPool(5,new DefaultThreadFactory("CONSUMER"));

    private ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue(10,true);


    public static void main(String[] args) throws Exception {
//        ProviderConsumerTest providerConsumerTest = new ProviderConsumerTest();
//        providerConsumerTest.init();
//        Thread.sleep(1000 * 2);
//        providerConsumerTest.getState();
        consumer.shutdown();
        consumer.execute(()->{
            System.out.println(123);
        });

    }

    private void getState(){
        System.out.println(consumer.isShutdown());
    }

    private void init(){
        for (int i = 0; i < 2; i++) {
            provider.submit(new Provider());
        }

        for (int i = 0; i < 2; i++) {
            consumer.submit(new Consumer());
        }
    }

    private class Provider implements Runnable{
        @Override
        public void run() {
            int i = 0;
            while (true){
                try{
                    queue.put(i);
                    i++;
                    if(i > 50){
                        queue.put(-1);
                        break;
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }

            }
            provider.shutdown();
            getState();
        }
    }

    private class Consumer implements Runnable{

        volatile boolean stop = false;

        @Override
        public void run() {
            int i = 0;
            while (true){
                try{
                    final Integer take = queue.take();

                    if(take < 0){
                        System.out.println(Thread.currentThread().getName() + "----stop");
                        if(queue.size() < 1 ){
                            queue.put(-1);
                        }
                        break;
                    }
                    System.out.println(take);
                } catch(Exception e){
                    e.printStackTrace();
                }

            }
            consumer.shutdown();

        }
    }
}
