package com.hyj.nio.socket.udp;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;

/**
 * 获取广播地址
 */
public class GetBroadCastIP {
    public static void main(String[] args) {
        try{
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()){
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                System.out.println("设备名称："+networkInterface.getDisplayName());
                List<InterfaceAddress> interfaceAddresses = networkInterface.getInterfaceAddresses();
                interfaceAddresses.forEach(t->{
                    InetAddress broadcast = t.getBroadcast();
                    if(broadcast != null){
                        System.out.println(broadcast.getHostAddress());
                    }
                });
            }
        } catch(Exception e){
            e.printStackTrace();
        }

    }
}
