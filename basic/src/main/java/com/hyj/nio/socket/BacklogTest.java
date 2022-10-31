package com.hyj.nio.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class BacklogTest {

    static class ServerT {

        public static void main(String[] args) throws IOException, InterruptedException {
                ServerSocket serverSocket = new ServerSocket(8081,3);

                TimeUnit.SECONDS.sleep(10);
                for (int i = 0; i < 200000; i++) {
                    System.out.println("acceptl begin " + (i + 1));
                    Socket socket = serverSocket.accept();
                    System.out.println("acceptl end" + (i + 1));
                }
                serverSocket.close();

        }

    }

    static class Clientt{

        public static void main(String[] args) {
            try{

                for (int i = 0;i <200000;i++){
                    Socket socket = new Socket("localhost",8081);
                }

            } catch(Exception e){
                e.printStackTrace();
            }

        }
    }

}
