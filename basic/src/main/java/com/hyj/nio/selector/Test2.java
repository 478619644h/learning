package com.hyj.nio.selector;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Test2 {

    static class S{
        public static void main(String[] args) {
            try{
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.bind(new InetSocketAddress("localhost",8888));
                SocketChannel accept = serverSocketChannel.accept();

                ByteBuffer buffer =ByteBuffer.allocate(2);
                int readLength = accept.read(buffer);

                while (readLength != -1){
                    String s = new String(buffer.array());
                    System.out.print(s);
                    buffer.flip();
                    readLength = accept.read(buffer);
                }
                accept.close();
                serverSocketChannel.close();

            } catch(Exception e){
                e.printStackTrace();
            }

        }
    }

    static class C{
        public static void main(String[] args) {
            try{
                Socket socket = new Socket("localhost", 8888);
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write("i am super man".getBytes());
                outputStream.close();
                socket.close();
            } catch(Exception e){
                e.printStackTrace();
            }

        }
    }

}
