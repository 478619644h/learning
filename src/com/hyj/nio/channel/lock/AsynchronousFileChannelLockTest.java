package com.hyj.nio.channel.lock;

import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AsynchronousFileChannelLockTest {

    static Path path = Paths.get("/Users/huangyujian/work/gitwork/learning/src/com/hyj/nio/channel/b.txt");

    static class Lock1{


        public static void main(String[] args) {

            try{

                AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
                System.out.println("get lock begin : " + System.currentTimeMillis());
                Future<FileLock> lock = fileChannel.lock();
                FileLock fileLock = lock.get();
                System.out.println("get lock end : " + System.currentTimeMillis());

                TimeUnit.SECONDS.sleep(5);
                fileLock.release();
                System.out.println("release lock : " + System.currentTimeMillis());

                fileChannel.close();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    static class Lock2{

        public static void main(String[] args) {
            try{

                AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
                System.out.println("get lock begin : " + System.currentTimeMillis());
                Future<FileLock> lock = fileChannel.lock();
                System.out.println("get lock end : " + System.currentTimeMillis());
                FileLock fileLock = lock.get();
                System.out.println("get loct time : " + System.currentTimeMillis());
                fileChannel.close();
            } catch(Exception e){
                e.printStackTrace();
            }
        }


    }

}
