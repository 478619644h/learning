package com.hyj.nio;


import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class ClientTest {

    public static void main(String[] args) {
        //client();
        System.out.println(Integer.bitCount(6));
    }

    public static void client() {
        while(true){
            try (Socket socket = new Socket("localhost", 8080);) {
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                out.println("123456");

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println(reader.readLine());
                TimeUnit.SECONDS.sleep(1L);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
