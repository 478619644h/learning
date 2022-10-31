package com.hyj.nio.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class TcpSharkTest {

   static class Server{
        public static void main(String[] args) {

            try{
                ServerSocket serverSocket = new ServerSocket(8081);
                System.out.println("阻塞开始 ：" + System.currentTimeMillis());
                Socket socket = serverSocket.accept();
                System.out.println("阻塞结束 ：" + System.currentTimeMillis());


                socket.close();
                serverSocket.close();
                TimeUnit.HOURS.sleep(1);

            } catch(Exception e){
                e.printStackTrace();
            }


        }
    }

    static class Client{

        public static void main(String[] args) {
            try{
                System.out.println("连接准备： " + System.currentTimeMillis());
                Socket socket = new Socket("127.0.0.1",8081);
                System.out.println("连接结束： " + System.currentTimeMillis());
                /*OutputStream outputStream = socket.getOutputStream();

                outputStream.write("111".getBytes());
                outputStream.write("11111".getBytes());
                outputStream.write("1111111111".getBytes());*/



                //outputStream.close();
                socket.close();
                TimeUnit.HOURS.sleep(1);

            } catch(Exception e){
                e.printStackTrace();
            }

        }
    }
}
