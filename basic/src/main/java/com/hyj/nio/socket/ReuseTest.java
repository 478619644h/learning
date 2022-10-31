package com.hyj.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class ReuseTest {


    static class T1{

        public static void main(String[] args) throws InterruptedException {
            Thread thread = new Thread() {
                @Override
                public void run() {

                    try {
                        ServerSocket serverSocket = new ServerSocket();
                        serverSocket.setReuseAddress(false);
                        serverSocket.bind(new InetSocketAddress("localhost",8888));
                        System.out.println("serverSocket.getReuseAddress() : " + serverSocket.getReuseAddress());
                        Socket accept = serverSocket.accept();

                        TimeUnit.SECONDS.sleep(1);

                        accept.close();
                        serverSocket.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            thread.start();

            TimeUnit.MILLISECONDS.sleep(500);

            new Thread(){

                @Override
                public void run() {
                    try {
                        Socket socket = new Socket("localhost",8888);
                        TimeUnit.SECONDS.sleep(3);
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }.start();


        }


    }

    static class T2{
        public static void main(String[] args) {
            try{
                ServerSocket serverSocket = new ServerSocket(8888);
                Socket accept = serverSocket.accept();

                accept.close();
                serverSocket.close();

            } catch(Exception e){
                e.printStackTrace();
            }

        }
    }
}
