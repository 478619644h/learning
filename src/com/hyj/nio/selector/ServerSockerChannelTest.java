package com.hyj.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerSockerChannelTest {


    static class S{
        public static void main(String[] args) {
            try {
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.bind(new InetSocketAddress("localhost",8888),60);

                ServerSocket serverSocket = serverSocketChannel.socket();

                TimeUnit.SECONDS.sleep(1);

                int i = 0;
                while (i < 61){
                    Socket accept = serverSocket.accept();
                    accept.close();
                    i++;
                }

                serverSocket.close();
                serverSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class C{

        volatile static AtomicInteger atomicInteger = new AtomicInteger(1);

        public static void main(String[] args) {
           try{
               for (int i = 0;i< 61;i++){
                  new Thread(){
                      @Override
                      public void run() {
                         try{
                             Socket socket = new Socket("localhost", 8888);
                             socket.close();
                             atomicInteger.incrementAndGet();
                         } catch(Exception e){
                             e.printStackTrace();
                         }

                      }
                  }.start();
               }
           } catch(Exception e){
               e.printStackTrace();
           }
            System.out.println(atomicInteger.get());

        }
    }
}
