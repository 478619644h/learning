package com.hyj.lock;

import org.apache.tools.ant.taskdefs.Sleep;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.StampedLock;

public class Test {

    private static double x, y;
    final static StampedLock sl = new StampedLock();
    static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
       // sl.writeLock();
        new Thread(()->{moveIfAtOrigin(1.2,1.3);}).start();
        new Thread(()->{

            long l = sl.readLock();
            System.out.println("拿到读锁");
            while (true){
                try {
                    countDownLatch.await();
                    break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            sl.unlockRead(l);
        }).start();
    }



    // 读写互斥
    static void moveIfAtOrigin(double newX, double newY) {
        long stamp = sl.readLock();
        try {
            while (x == 0.0 && y == 0.0) {
                Thread.sleep(100);
                //stamp 为写锁时 不会转换写锁id
                // 其他线程持有写锁时 不能升级为写锁
                long ws = sl.tryConvertToWriteLock(stamp);
                if (ws != 0L) {
                    //问题出在没有对stamp重新赋值
                    // 新增下面一行
                    stamp = ws;
                    x = newX;
                    y = newY;
                    break;
                } else {
                    sl.unlockRead(stamp);
                    countDownLatch.countDown();
                    stamp = sl.writeLock();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //此处unlock的是stamp
            sl.unlock(stamp);
        }
    }
}
