package com.hyj.nio.selector;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class SeletorTest {

    static class BlokingModle{
        public static void main(String[] args) {
            try{
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.bind(new InetSocketAddress(8888));
                //默认为阻塞模式
                System.out.println("serverSocketChannel.isBlocking() = " + serverSocketChannel.isBlocking());

                Selector selector = Selector.open();
                //阻塞模式会报异常 IllegalBlockingModeException
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


            } catch(Exception e){
                e.printStackTrace();
            }

        }
    }

    static class NoneBlockingModle{

        public static void main(String[] args) {
            try{

                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.bind(new InetSocketAddress(8888));
                serverSocketChannel.configureBlocking(false);

                System.out.println("serverSocketChannel.isBlocking() = " + serverSocketChannel.isBlocking());

                Selector selector = Selector.open();

                SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

                System.out.println("selectionKey.isAcceptable() = " + selectionKey.isAcceptable());

                selector.close();
                serverSocketChannel.close();
            } catch(Exception e){
                e.printStackTrace();
            }

        }
    }

}
