package com.hyj.nio.timeserver.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * nio 多路复用 实现的 时间服务
 */
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

        MiltiplexerTimeServer miltiplexerTimeServer = new MiltiplexerTimeServer(port);

        new Thread(miltiplexerTimeServer,"NIO-miltiplexerTimeServer-001").start();

    }

    static class MiltiplexerTimeServer implements Runnable{

        private Selector selector;

        private ServerSocketChannel serverSocketChannel;

        private volatile boolean stop;

        public MiltiplexerTimeServer(int port) {
            try{
                this.selector = Selector.open();
                this.serverSocketChannel = ServerSocketChannel.open();
                this.serverSocketChannel.configureBlocking(false);
                this.serverSocketChannel.bind(new InetSocketAddress(port),1024);
                this.serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
                System.out.println("the time server is start in port : " + port);
            } catch(Exception e){
                e.printStackTrace();
                System.exit(1);
            }

        }
        public void stop(){
            this.stop = true;
        }

        @Override
        public void run() {
            while (!stop){
               try{
                   this.selector.select(1000);
                   Set<SelectionKey> selectionKeys = this.selector.selectedKeys();
                   Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
                   SelectionKey selectionKey = null;
                   while (keyIterator.hasNext()){
                       selectionKey = keyIterator.next();
                       keyIterator.remove();
                       try{
                            handleInput(selectionKey);
                       } catch(Exception e){
                           if(selectionKey != null){
                               selectionKey.cancel();
                               if(selectionKey.channel() != null){
                                   selectionKey.channel().close();
                               }
                           }

                       }


                   }
               } catch(Exception e){
                   e.printStackTrace();
                   System.exit(1);
               }
            }

            if(this.selector != null){
                try {
                    //多路复用器上的channel&pipe 会随着selector的关闭自动关闭
                    this.selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleInput(SelectionKey key) throws IOException {
            if(key.isValid()){
                //处理新接入的请求
                if(key.isAcceptable()){
                    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    sc.register(this.selector,SelectionKey.OP_READ);
                }

                if(key.isReadable()){
                    SocketChannel sc = (SocketChannel) key.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    int read = sc.read(readBuffer);
                    if(read > 0){
                        readBuffer.flip();
                        byte[] bytes = new byte[readBuffer.remaining()];
                        readBuffer.get(bytes);
                        String body = new String(bytes, "UTF-8");
                        System.out.println("the server receive order : " + body);
                        String currentTime = body==null&&"QUERY TIME ORDER".equals(body)?
                                "BAD ORDER":new Date().toString();
                        doWrite(sc,currentTime);
                    } else if(read < 0){
                        key.cancel();
                        sc.close();
                    } else {
                        //读到0字节,忽略
                        ;
                    }

                }
            }
        }

        private void doWrite(SocketChannel sc,String response) throws IOException {
            if(response != null && response.trim().length() > 0){
                byte[] bytes = response.getBytes();
                ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
                byteBuffer.put(bytes);
                byteBuffer.flip();
                sc.write(byteBuffer);
            }
        }

    }

}
