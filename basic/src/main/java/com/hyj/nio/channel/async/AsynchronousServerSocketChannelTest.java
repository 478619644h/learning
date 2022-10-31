package com.hyj.nio.channel.async;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class AsynchronousServerSocketChannelTest {


    static class S {

        public static void main(String[] args) throws InterruptedException, ExecutionException, Exception {

            AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel
                    .open()
                    .bind(new InetSocketAddress(8888));

            serverSocketChannel.accept("I am server", new CompletionHandler<AsynchronousSocketChannel, String>() {
                @Override
                public void completed(AsynchronousSocketChannel result, String attachment) {
                    try{
                        serverSocketChannel.accept(null, this);
                        System.out.println(" public void completed Thread :" + Thread.currentThread().getName());

                        ByteBuffer allocate = ByteBuffer.allocate(14);
                        Future<Integer> read = result.read(allocate);
                        System.out.println(new String(allocate.array(), 0, read.get()));
                        result.close();
                    } catch(Exception e){
                        e.printStackTrace();
                    }

                }

                @Override
                public void failed(Throwable exc, String attachment) {

                }
            });

            while (true);

        }

    }


    static class S2{

        public static void main(String[] args) {

            try {
                AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel
                        .open()
                        .bind(new InetSocketAddress(8888));

                Future<AsynchronousSocketChannel> accept = serverSocketChannel.accept();
                AsynchronousSocketChannel asynchronousSocketChannel = accept.get();
                ByteBuffer allocate = ByteBuffer.allocate(7);
                Future<Integer> read = asynchronousSocketChannel.read(allocate);
                System.out.println(new String(allocate.array(),0,read.get()));
                while (true);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }

    }

    static class C1{
        public static void main(String[] args) {
            try (Socket socket = new Socket("localhost", 8888);
                 OutputStream outputStream = socket.getOutputStream();) {

                outputStream.write("I am C1".getBytes());
                outputStream.flush();

            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    static class C2{

        public static void main(String[] args) throws IOException, InterruptedException {

            AsynchronousSocketChannel open = AsynchronousSocketChannel.open();
            open.connect(new InetSocketAddress("localhost", 8888), null, new CompletionHandler<Void, Object>() {
                @Override
                public void completed(Void result, Object attachment) {
                    try{
                            Future<Integer> future = open.write(ByteBuffer.wrap("I am C2".getBytes()));
                            System.out.println("写入长度 ："+future.get());
                        Future<Integer> future1 = open.write(ByteBuffer.wrap("I am C2".getBytes()));
                        System.out.println("写入长度 ："+future1.get());
                        open.close();
                    } catch(Exception e){
                        e.printStackTrace();
                    }


                }

                @Override
                public void failed(Throwable exc, Object attachment) {

                }
            });

            TimeUnit.SECONDS.sleep(1);

        }

    }

}
