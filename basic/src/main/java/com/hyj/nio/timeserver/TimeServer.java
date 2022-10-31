package com.hyj.nio.timeserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * @description Oio 实现的查询时间服务端
 *
 */
public class TimeServer {

    private static int port = 8080;

    public static void main(String[] args) {

        if(args != null && args.length > 0){
            try{
                port = Integer.parseInt(args[0]);
            } catch(NumberFormatException e){
                e.printStackTrace();
            }

        }

        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("the time server start in port: " + port);
            Socket socket = null;
            while (true){
                socket = serverSocket.accept();
                new Thread(new TimeServerHandle(socket)).start();
            }

        } catch(Exception e){
            e.printStackTrace();
        }


    }

    static class TimeServerHandle implements Runnable{

        private Socket socket;

        public TimeServerHandle(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try(BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                PrintWriter out = new PrintWriter(this.socket.getOutputStream(),true)){
                String currentTime = null;
                String readLine = null;
                while (true){
                    readLine = in.readLine();
                    if(readLine == null){
                        break;
                    }
                    System.out.println("the time server receive order : " + readLine);
                    currentTime = "QUERY TIME ORDER".equals(readLine)?new Date().toString():"BAD ORDER";
                    out.println(currentTime);
                }

            } catch(Exception e){
                e.printStackTrace();
            }

        }
    }

}
