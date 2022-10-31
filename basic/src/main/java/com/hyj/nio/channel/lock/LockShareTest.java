package com.hyj.nio.channel.lock;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;

public class LockShareTest {

    private static String filePath =  "./a.txt";

    public static void main(String[] args) {
        try(RandomAccessFile file = new RandomAccessFile(filePath,"rw");
            FileChannel channel = file.getChannel()){

            channel.lock(1,100,false);

            TimeUnit.HOURS.sleep(1);
        } catch(Exception e){
            e.printStackTrace();
        }

    }

}
