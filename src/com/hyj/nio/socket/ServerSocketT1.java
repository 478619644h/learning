package com.hyj.nio.socket;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketT1 {

    public static void main(String[] args) {
        //byte[] bytes = new byte[1024];
        char[] chars = new char[3];
        try (ServerSocket serverSocket = new ServerSocket(8081)) {
            Socket socket = serverSocket.accept();
            //输入开始
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            int readLength = objectInputStream.readInt();
            byte[] bytes = new byte[readLength];
            objectInputStream.readFully(bytes);
            System.out.println(new String(bytes));



            //输出开始
            OutputStream outputStream = socket.getOutputStream();
            String a = "客户端你好 A \n";
            String b = "客户端你好 B \n";
            String c = "客户端你好 C \n";

            int allLength = (a+b+c).getBytes().length;
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeInt(allLength);
            objectOutputStream.flush();
            objectOutputStream.write(a.getBytes());
            objectOutputStream.write(b.getBytes());
            objectOutputStream.write(c.getBytes());
            objectOutputStream.flush();

            readLength = objectInputStream.readInt();
            bytes = new byte[readLength];
            objectInputStream.readFully(bytes);
            System.out.println(new String(bytes));

            String d = "客户端你好 D \n";
            String e = "客户端你好 E \n";
            String f = "客户端你好 F \n";

            allLength = (d+e+f).getBytes().length;
            objectOutputStream.writeInt(allLength);
            objectOutputStream.flush();
            objectOutputStream.write(d.getBytes());
            objectOutputStream.write(e.getBytes());
            objectOutputStream.write(f.getBytes());
            objectOutputStream.flush();

            inputStream.close();
            outputStream.close();



        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
