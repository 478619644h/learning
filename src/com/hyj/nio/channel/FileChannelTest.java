package com.hyj.nio.channel;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest {


    private static String filePath = "/Users/huangyujian/work/gitwork/learning/src/com/hyj/nio/channel/a.txt";

    static FileInputStream fileIutputStream;
    static FileChannel fileChannel;
    static FileOutputStream fileOutputStream;

    public static void main(String[] args) throws IOException, InterruptedException {
        /*fileIutputStream = new FileInputStream(new File(filePath));
        fileChannel = fileIutputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(5);
        int readLength = fileChannel.read(buffer);
        System.out.println(readLength);

        buffer.clear();
        fileChannel.position(0);
        readLength = fileChannel.read(buffer);
        System.out.println(readLength);


        fileIutputStream.close();
        fileChannel.close();*/


//        ByteBuffer buffer = ByteBuffer.wrap("123456".getBytes());
//        buffer.limit(3);
//        buffer.position(2);
//
//        System.out.println(buffer.remaining());

//        fileOutputStream = new FileOutputStream(new File(filePath));
//        fileChannel = fileOutputStream.getChannel();
//        System.out.println(fileChannel.position());
//
//        fileChannel.write(ByteBuffer.wrap("123456".getBytes()));
//        System.out.println(fileChannel.position());
//        fileChannel.position(10);
//        fileChannel.write(ByteBuffer.wrap("123456".getBytes()));
//        System.out.println(fileChannel.position());
//        fileOutputStream.close();
//        fileChannel.close();

        RandomAccessFile randomAccessFile = new RandomAccessFile(filePath,"rw");
        fileChannel = randomAccessFile.getChannel();
        fileChannel.lock(1,2,false);
        Thread.sleep(Integer.MAX_VALUE);

    }

}
