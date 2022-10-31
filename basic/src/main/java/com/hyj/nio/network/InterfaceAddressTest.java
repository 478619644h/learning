package com.hyj.nio.network;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

public class InterfaceAddressTest {

    public static void main(String[] args) {

        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            while (networkInterfaces.hasMoreElements()){
                NetworkInterface networkInterface = networkInterfaces.nextElement();

                System.out.println("网络名称 ：" + networkInterface.getName());
                System.out.println("网络设备显示名称 ：" + networkInterface.getDisplayName());

                System.out.println("是不是点对点设备 ：" + networkInterface.isPointToPoint());

                System.out.println("是否支持多地址广播：" + networkInterface.supportsMulticast());
               // List<InterfaceAddress> interfaceAddresses = networkInterface.getInterfaceAddresses();

                /*interfaceAddresses.forEach(t->{

                    InetAddress address = t.getAddress();
                    if (address != null){
                        System.out.println("address.getHostAddress() :" + address.getHostAddress());
                    }

                    InetAddress broadcast = t.getBroadcast();
                    if(broadcast != null){
                        System.out.println("broadcast.getHostAddress() : " + broadcast.getHostAddress());
                    }
                    System.out.println("t.getNetworkPrefixLength() :" + t.getNetworkPrefixLength());
                    System.out.println();

                });*/

                System.out.println();
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

}
