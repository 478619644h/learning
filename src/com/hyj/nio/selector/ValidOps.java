package com.hyj.nio.selector;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ValidOps {

    public static void main(String[] args) {

        try{
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            SocketChannel socketChannel = SocketChannel.open();

            System.out.println("serverSocketChannel.validOps() = " + serverSocketChannel.validOps());
            System.out.println("socketChannel.validOps() = " + socketChannel.validOps());

            System.out.println("serverSocketChannel suppord ops:");
            System.out.println(SelectionKey.OP_ACCEPT & ~serverSocketChannel.validOps());
            System.out.println(SelectionKey.OP_ACCEPT);
            System.out.println(~serverSocketChannel.validOps());


            serverSocketChannel.close();
            socketChannel.close();
        } catch(Exception e){
            e.printStackTrace();
        }

    }
}
