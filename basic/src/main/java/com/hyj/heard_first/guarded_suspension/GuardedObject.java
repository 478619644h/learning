package com.hyj.heard_first.guarded_suspension;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * 保护性暂挂模式
 * 一个 Web 项目：Web 版的文件浏览器，通过它用户可以在浏览器里查看服务器上的目录和文件。
 * 这个项目依赖运维部门提供的文件浏览服务，而这个文件浏览服务只支持消息队列（MQ）方式接入。
 * 消息队列在互联网大厂中用的非常多，主要用作流量削峰和系统解耦。在这种接入方式中，
 * 发送消息和消费结果这两个操作之间是异步的
 * <p>
 * 这个 Web 项目中，用户通过浏览器发过来一个请求，
 * 会被转换成一个异步消息发送给 MQ，等 MQ 返回结果后，再将这个结果返回至浏览器。
 * 小灰同学的问题是：给 MQ 发送消息的线程是处理 Web 请求的线程 T1，但消费 MQ 结果的线程并不是线程 T1
 * 那线程 T1 如何等待 MQ 的返回结果呢？
 */
public class GuardedObject<T> {

    private T obj;

    final Lock lock = new ReentrantLock();

    final Condition done = lock.newCondition();

    final CountDownLatch countDownLatch = new CountDownLatch(1);

    final int timeout = 2;
    //保存所有GuardedObject
    final static Map<Object, GuardedObject> gos = new ConcurrentHashMap<>();

    //静态方法创建 GuardedObject
    static <K> GuardedObject create(K key) {
        GuardedObject go = new GuardedObject();
        gos.put(key, go);
        return go;
    }

    static <K, T> void fireEvent(K key, T obj) {
        GuardedObject go = gos.remove(key);
        if (go != null) {
            go.onChanged(obj);
        }
    }

    //获取受保护对象
    T get(Predicate p) {
        //lock.lock();
        try {
            //MESA管程推荐写法
            while (!p.test(obj)) {
                countDownLatch.await(timeout, TimeUnit.SECONDS);
                //done.await(timeout, TimeUnit.SECONDS);
            }
        } catch (
                InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            //lock.unlock();
        }
        //返回非空的受保护对象
        return obj;
    }

    //事件通知方法
    void onChanged(T obj) {
        //lock.lock();
        try {
            this.obj = obj;
            //done.signalAll();
            countDownLatch.countDown();
        } finally {
            //lock.unlock();
        }
    }


}
