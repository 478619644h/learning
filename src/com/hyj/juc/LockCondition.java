package com.hyj.juc;

import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用juc包提供的condition 监视器实现线程协调 不用传统的wait notify
 * 且传统的wait notify无法实现多线程的顺序执行
 */
public class LockCondition {
    public static void main(String[] args) {
        Data data = new Data();
        new Thread(()->{
            for (int i = 1; i <= 5; i++){
                data.pring5();
            }
        },"A").start();

        new Thread(()->{
            for (int i = 1; i <= 5; i++){
                data.pring10();
            }
        },"B").start();

        new Thread(()->{
            for (int i = 1; i <= 5; i++){
                data.pring15();
            }
        },"C").start();
    }
}


class Data {

    private int num = 1;

    private Lock lock = new ReentrantLock();

    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void pring5() {
        lock.lock();
        try {
            //判断
            while (num != 1) {
                condition1.await();
            }
            //执行
            num = 2;
            System.out.print(Thread.currentThread().getName());
            for (int i = 1;i <= 5; i++){
                System.out.print("  " + i);
            }
            System.out.println();
            //唤醒
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void pring10() {
        lock.lock();
        try {
            //判断
            while (num != 2) {
                condition2.await();
            }
            //执行
            num = 3;
            System.out.print(Thread.currentThread().getName());
            for (int i = 1;i <= 10; i++){
                System.out.print("  " + i);
            }
            System.out.println();
            //唤醒
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void pring15() {
        lock.lock();
        try {
            //判断
            while (num != 3) {
                condition3.await();
            }
            //执行
            num = 1;
            System.out.print(Thread.currentThread().getName());
            for (int i = 1;i <= 15; i++){
                System.out.print("  " + i);
            }
            System.out.println();
            //唤醒
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


}
