package com.hyj.nio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class FileChannelTest {

    public static void main(String[] args) throws IOException {
        //transferTo();
        selectorTest(null);

    }



    public static void test() {
        try (RandomAccessFile randomAccessFile =
                     new RandomAccessFile("/Users/huangyujian/work/gitwork/learning/src/com/hyj/nio/nio-test.txt",
                             "rw")) {

            FileChannel fileChannel = randomAccessFile.getChannel();


            ByteBuffer buffer = ByteBuffer.allocate(1);
            //byte及字节 1byte=8bit 汉子utf8占三个字节
            int bytesRead = fileChannel.read(buffer);

            while (bytesRead != -1) {
                //写状态转为读状态 将 position设置为0  limit 设置为之前的position的值
                buffer.flip();
                while (buffer.hasRemaining()) {
                    System.out.print(Charset.forName("utf-8").decode(buffer));
                }
                buffer.clear();
                bytesRead = fileChannel.read(buffer);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void transferTo() {
        try (RandomAccessFile randomAccessFile =
                     new RandomAccessFile("/Users/huangyujian/work/gitwork/learning/src/com/hyj/nio/nio-test.txt",
                             "rw");
             RandomAccessFile toFile =
                     new RandomAccessFile("/Users/huangyujian/work/gitwork/learning/src/com/hyj/nio/nio-to.txt",
                             "rw")) {

            Channel from = randomAccessFile.getChannel();
            Channel to = toFile.getChannel();

            ((FileChannel) from).transferTo(0, ((FileChannel) from).size(), (WritableByteChannel) to);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void selectorTest(ServerSocketChannel channel) throws IOException {
        try (Selector selector = Selector.open()) {
            // 打开服务器的通道
            ServerSocketChannel initSer = ServerSocketChannel.open();
            // 服务器配置为非阻塞
            initSer.configureBlocking(false);
            ServerSocket initSock = initSer.socket();
            // 实例化绑定地址
            InetSocketAddress address = new InetSocketAddress(8080);
            // 进行服务的绑定
            initSock.bind(address);
            initSer.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                int readChannels = selector.select();
                if (readChannels == 0) {
                    continue;
                }
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isConnectable()) {
                        System.out.println("c");
                    } else if (selectionKey.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel client = server.accept();    // 接收新连接
                        client.configureBlocking(false);// 配置为非阻塞
                        ByteBuffer outBuf = ByteBuffer.allocate(10);
                        int bytesRead = client.read(outBuf);

                        while (bytesRead != -1) {
                            //写状态转为读状态 将 position设置为0  limit 设置为之前的position的值
                            outBuf.flip();
                            while (outBuf.hasRemaining()) {
                                System.out.print(Charset.forName("utf-8").decode(outBuf));
                            }
                            outBuf.rewind();
                            while (outBuf.hasRemaining()){
                                client.write(outBuf);
                            }
                            outBuf.clear();
                            bytesRead = client.read(outBuf);
                        }
                        client.close();
                    } else if (selectionKey.isReadable()) {
                        System.out.println("r");
                    } else if (selectionKey.isWritable()) {
                        System.out.println("w");
                    }
                    iterator.remove();
                }

            }
        }

    }


}
