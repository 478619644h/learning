package com.hyj.nio.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class UdpTest {


    static class Server{
        public static void main(String[] args) {

            try {
                DatagramSocket datagramSocket = new DatagramSocket(7777);

                byte[] bytes = new byte[6];
                DatagramPacket datagramPacket = new DatagramPacket(bytes,bytes.length);
                datagramSocket.receive(datagramPacket);

                String getDate = new String(datagramPacket.getData(),0,datagramPacket.getLength());

                System.out.println(getDate);

                datagramSocket.close();


            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    static class Client{

        public static void main(String[] args) {
            try {
                DatagramSocket datagramSocket = new DatagramSocket();
                datagramSocket.connect(new InetSocketAddress("localhost",7777));
                String sendDate = "";
                for(int i = 0;i < 3;i++){
                    sendDate += "a";
                }
                sendDate += "end";
                byte[] bytes = sendDate.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(bytes,bytes.length);

                datagramSocket.send(datagramPacket);

                datagramSocket.close();
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
