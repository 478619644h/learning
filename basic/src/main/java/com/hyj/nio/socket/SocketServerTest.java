package com.hyj.nio.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class SocketServerTest {


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8088);
            Socket accept = serverSocket.accept();

            InputStream inputStream = accept.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String getString = "";
            while (!"".equals(reader.readLine())){
                getString = reader.readLine();
                System.out.println(getString);
            }

            OutputStream outputStream = accept.getOutputStream();
            outputStream.write("HTTP/1.1 200 OK\r\n\r\n". getBytes ());
            outputStream.write(("<html><body><a href= 'http://www.baidu.com'> i am baidu.com " +
                    "welcome you !</a></body></html>").getBytes());
            outputStream.flush();

            TimeUnit.SECONDS.sleep(3);
            inputStream.close();
            outputStream.close();
            accept.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
