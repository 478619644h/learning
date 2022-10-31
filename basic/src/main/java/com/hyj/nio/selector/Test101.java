package com.hyj.nio.selector;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Test101 {

    static class S{

        public static void main(String[] args) {
            try{

                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.bind(new InetSocketAddress("localhost",8888));
                serverSocketChannel.configureBlocking(false);

                Selector selector = Selector.open();
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

                while (true){
                    int keyCount = selector.select();
                    //全部的键集
                    Set<SelectionKey> keys = selector.keys();
                    //已就绪的键集
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();

                    Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();

                    while (selectionKeyIterator.hasNext()){
                        SelectionKey selectionKey = selectionKeyIterator.next();
                        if(selectionKey.isAcceptable()){
                            ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                            SocketChannel socketChannel = channel.accept();
                            ByteBuffer buffer = ByteBuffer.allocate(2);

                            int read = socketChannel.read(buffer);
                            while (read != -1){
                                String s = new String(buffer.array());
                                System.out.print(s);
                                buffer.flip();
                                read = socketChannel.read(buffer);
                            }
                            socketChannel.close();
                            selectionKeyIterator.remove();
                        }
                    }
                    System.out.println();
                }

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
                outputStream.write("I am super man".getBytes());

                outputStream.close();
                socket.close();
            } catch(Exception e){
                e.printStackTrace();
            }

        }
    }

}
