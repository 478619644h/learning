package com.hyj.nio.timeserver.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class TimeServer {

    private static int port = 8080;

    public static void main(String[] args) {
        if(args != null && args.length > 0){
            try{
                port = Integer.parseInt(args[0]);
            } catch(Exception e){
                e.printStackTrace();
            }

        }

        AsyncTimeServerHandler asyncTimeServerHandler = new AsyncTimeServerHandler();
        new Thread(asyncTimeServerHandler).start();

    }

    static class AsyncTimeServerHandler implements Runnable{

        private AsynchronousServerSocketChannel asynchronousServerSocketChannel;

        private CountDownLatch latch;

        public AsyncTimeServerHandler() {

          try{
              asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
              asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
              System.out.println("the server is start in port : " + port);
          } catch(Exception e){
              e.printStackTrace();
          }

        }

        @Override
        public void run() {
            latch = new CountDownLatch(1);
            doAccept();
            try{
                latch.await();
            } catch(Exception e){
                e.printStackTrace();
            }

        }

        private void doAccept(){
            asynchronousServerSocketChannel.accept(this,new AcceptCompletionHandler());
        }
    }

    static class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel,AsyncTimeServerHandler>{

        @Override
        public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
            attachment.asynchronousServerSocketChannel.accept(attachment,this);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            result.read(buffer,buffer,new ReadCompletionHandler(result));
        }

        @Override
        public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
            exc.printStackTrace();
            attachment.latch.countDown();
        }
    }

    static class ReadCompletionHandler implements CompletionHandler<Integer,ByteBuffer>{

        private AsynchronousSocketChannel asynchronousSocketChannel;

        public ReadCompletionHandler(AsynchronousSocketChannel asynchronousSocketChannel) {
            this.asynchronousSocketChannel = asynchronousSocketChannel;
        }



        @Override
        public void completed(Integer result, ByteBuffer attachment) {
            attachment.flip();
            byte[] bytes = new byte[attachment.remaining()];
            attachment.get(bytes);
            try{
                String body = new String(bytes,"UTF-8");
                System.out.println("the server receive order:  " + body);
                String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)?new Date().toString():"BAD ORDER";
                doWrite(currentTime);
            } catch(Exception e){
                e.printStackTrace();
            }

        }

        private void doWrite(String time){
            if(time != null && time.trim().length() > 0){
                byte[] bytes = time.getBytes();
                ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
                buffer.put(bytes);
                buffer.flip();
                this.asynchronousSocketChannel.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        //如果没发送完继续发送
                       if(attachment.hasRemaining()){
                           asynchronousSocketChannel.write(attachment,attachment,this);
                       }
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        try{
                            asynchronousSocketChannel.close();
                        } catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                });
            }
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            try{
                this.asynchronousSocketChannel.close();
            } catch(Exception e){
                e.printStackTrace();
            }

        }
    }

}
