package com.hyj.nio.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;

public class FIleChannelLock {

    private static String filePath = "/Users/huangyujian/work/gitwork/learning/src/com/hyj/nio/channel/a.txt";
    static FileChannel fileChannel;

    public static void main(String[] args) throws IOException, InterruptedException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(filePath,"rw");
        fileChannel = randomAccessFile.getChannel();
       // fileChannel.lock(1,2,true);
       // System.out.println("zuse");
        fileChannel.write(ByteBuffer.wrap("12346".getBytes()));

        TimeUnit.SECONDS.sleep(10);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try( RandomAccessFile file = new RandomAccessFile(filePath,"rw");
                     FileChannel fileChannel1 = file.getChannel();){
                    //file.getChannel().lock(1,2,false);
                    fileChannel1.write(ByteBuffer.wrap("1234".getBytes()));
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();


        fileChannel.close();
        randomAccessFile.close();
    }
}
