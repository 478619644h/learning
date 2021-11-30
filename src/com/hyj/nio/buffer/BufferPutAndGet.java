package com.hyj.nio.buffer;

import java.nio.ByteBuffer;

public class BufferPutAndGet {


    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        byte[] src = {123, 124, 125};
        buffer.put(src, 1, 2);
        System.out.println(buffer);

        for (byte b : buffer.array()) {
            System.out.print(b);
            System.out.print("-");
        }

        byte[] dst = new byte[4];
        buffer.rewind();
        buffer.get(dst, 0, 1);

        System.out.println();
        for (byte b : dst) {
            System.out.print(b);
            System.out.print("-");
        }
        System.out.println();
        beastGet();
        System.out.println();
        beastPut();
    }


    /**
     * 从缓冲区中获得数据
     * 有可能取多或取少的情况，那么可以使用如下的示例代码进行解决
     */
    public static void beastGet() {
        byte[] byteArrayin = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        ByteBuffer bytebuffer = ByteBuffer.wrap(byteArrayin);
        byte[] byteArrayOut = new byte[5];
        while (bytebuffer.hasRemaining()) {
            int readLength = Math.min(bytebuffer.remaining(), byteArrayOut.length);
            bytebuffer.get(byteArrayOut, 0, readLength);
            for (int i = 0; i < readLength; i++) {
                System.out.print(byteArrayOut[i] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 向缓冲区中写入数据
     * 有可能写多或写少的情况，那么可以使用如下的示例代码进行解决
     */
    public static void beastPut() {
        byte[] byteArrayinl = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        ByteBuffer bytebuffer = ByteBuffer.allocate(10);
        int getArrayindex = 0;
        while (getArrayindex < byteArrayinl.length) {
            //下面代码的作用就是判断 缓冲区的剩余和数组的剩余谁少
            int readLength = Math.min(bytebuffer.remaining(), byteArrayinl.length - getArrayindex);
            bytebuffer.put(byteArrayinl, getArrayindex, readLength);
            bytebuffer.flip();
            byte[] getArray = bytebuffer.array();
            for (int i = 0; i < bytebuffer.limit(); i++) {
                System.out.print(getArray[i] + " ");
            }
            getArrayindex = getArrayindex + readLength;
            System.out.println();
            bytebuffer.clear();

        }

    }
}
