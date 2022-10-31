package com.hyj.nio.timeserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @description Oio 实现的查询时间客户端
 *
 *
 */
public class TimeClient {

    private static String ip = "127.0.0.1";

    private static int port = 8080;

    public static void main(String[] args) {
        if(args != null && args.length > 0){
            try{
                port = Integer.parseInt(args[0]);
            } catch(NumberFormatException e){
                e.printStackTrace();
            }

        }
        try(Socket socket = new Socket(ip,port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true)){

            out.println("QUERY TIME ORDER");
            System.out.println("send order 2 server succeed");
            String receive = in.readLine();

            System.out.println("Now is : " + receive);


        } catch(Exception e){
            e.printStackTrace();
        }

    }
}
