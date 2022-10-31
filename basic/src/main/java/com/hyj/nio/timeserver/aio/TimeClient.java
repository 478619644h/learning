package com.hyj.nio.timeserver.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class TimeClient {

    private static String host = "127.0.0.1";

    private static int port = 8080;

    public static void main(String[] args) {
        if(args != null && args.length > 0){
            try{
                port = Integer.parseInt(args[0]);
                host = args[1];
            } catch(Exception e){
                e.printStackTrace();
            }

        }

        new Thread(new AscncTimeClientHandler()).start();
    }


    static class AscncTimeClientHandler implements CompletionHandler<Void,AscncTimeClientHandler>,Runnable{

        private AsynchronousSocketChannel asynchronousSocketChannel;
        private CountDownLatch latch;

        public AscncTimeClientHandler() {
            try {
                asynchronousSocketChannel = AsynchronousSocketChannel.open();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            latch = new CountDownLatch(1);
            asynchronousSocketChannel.connect(new InetSocketAddress(host,port),this,this);
            try{
                latch.await();
            } catch(InterruptedException e){
                e.printStackTrace();
            }

            try {
                asynchronousSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void completed(Void result, AscncTimeClientHandler attachment) {
            byte[] bytes = "QUERY TIME ORDER".getBytes();
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            asynchronousSocketChannel.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    if (attachment.hasRemaining()){
                        asynchronousSocketChannel.write(attachment,attachment,this);
                    } else {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        asynchronousSocketChannel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                            @Override
                            public void completed(Integer result, ByteBuffer attachment) {
                                attachment.flip();
                                byte[] readBytes = new byte[attachment.remaining()];
                                attachment.get(readBytes);
                                try {
                                    String msg = new String(readBytes,"UTF-8");
                                    System.out.println("Now is : " + msg);
                                    latch.countDown();
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }


                            @Override
                            public void failed(Throwable exc, ByteBuffer attachment) {
                                exc.printStackTrace();
                                latch.countDown();
                                try {
                                    asynchronousSocketChannel.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {

                }
            });
        }

        @Override
        public void failed(Throwable exc, AscncTimeClientHandler attachment) {
            exc.printStackTrace();
            latch.countDown();
            try {
                asynchronousSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
