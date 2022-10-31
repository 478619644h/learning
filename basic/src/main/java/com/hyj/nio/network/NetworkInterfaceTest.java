package com.hyj.nio.network;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class NetworkInterfaceTest {


    public static void main(String[] args) {
        try{
            /**
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()){
                NetworkInterface networkInterface = networkInterfaces.nextElement();

                //System.out.println("网络名称 ：" + networkInterface.getName());
//                System.out.println("网络设备显示名称 ：" + networkInterface.getDisplayName());
//                System.out.println("网络接口的索引 ：" + networkInterface.getIndex());
//                System.out.println("是否开启并运行 ： " + networkInterface.isUp());
//                System.out.println("是否为回调接口 ： " + networkInterface.isLoopback());
//
//                System.out.println("最大传输单元MTU=" + networkInterface.getMTU());
                //虚拟接口就是软件模拟的 没有父网络接口
//                System.out.println("是否虚拟接口：" + networkInterface.isVirtual());
//                System.out.println("是否有父接口：" + (networkInterface.getParent() != null));

                //设备地址 硬件地址 MAC地址三者的意义是一样的
                //System.out.println("硬件地址：" + networkInterface.getHardwareAddress());

                //获取子接口 windows不支持
//                Enumeration<NetworkInterface> subInterfaces = networkInterface.getSubInterfaces();
//                while (subInterfaces.hasMoreElements()){
//                    NetworkInterface subInterface = subInterfaces.nextElement();
//
//                    System.out.println("子接口的网络名称："+subInterface.getName());
//                }

                //获取IP信息
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()){
                    InetAddress inetAddress = inetAddresses.nextElement();
                    System.out.println("获取此ip地址的完全限定名 : " + inetAddress.getCanonicalHostName());
                    System.out.println("主机名：" + inetAddress.getHostName());
                    System.out.println("IP地址："+inetAddress.getHostAddress());
                    System.out.print("原始ip地址 byte[] :" );

                    byte[] ip = inetAddress.getAddress();
                    for (byte b : ip) {
                        System.out.print(b + " ");
                    }
                    System.out.println();
                    System.out.println();
                }



                System.out.println();
                System.out.println();

            }
            */
            InetAddress inetAddress = InetAddress.getByName("www.baidu.com");
            System.out.println("获取此ip地址的完全限定名 : " + inetAddress.getCanonicalHostName());
            System.out.println("主机名：" + inetAddress.getHostName());
            System.out.println("IP地址："+inetAddress.getHostAddress());
            System.out.print("原始ip地址 byte[] :" );
            byte[] ip = inetAddress.getAddress();
            for (byte b : ip) {
                System.out.print(b + " ");
            }

            InetAddress[] inetAddresses = InetAddress.getAllByName("baidu.com");
            for (InetAddress address : inetAddresses) {
                System.out.println("获取此ip地址的完全限定名 : " + address.getCanonicalHostName());
                System.out.println("主机名：" + address.getHostName());
                System.out.println("IP地址："+address.getHostAddress());
                System.out.print("原始ip地址 byte[] :" );
                byte[] ip1 = address.getAddress();
                for (byte b : ip1) {
                    System.out.print(b + " ");
                }
            }


        } catch(Exception e){
            e.printStackTrace();
        }

    }

}
