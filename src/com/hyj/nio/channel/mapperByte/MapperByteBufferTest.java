package com.hyj.nio.channel.mapperByte;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MapperByteBufferTest {


    public static void main(String[] args) {
        try(RandomAccessFile file = new RandomAccessFile("../a.txt","rw");
            FileChannel channel = file.getChannel()){
            //返回在内存的映射map
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 1, 100);
            //是否加载到内存  不完全确定
            System.out.println(map.isLoaded());
            //加载   windows无效
            map.load();
            System.out.println(map.isLoaded());
        } catch(Exception e){
            e.printStackTrace();
        }

    }
}
