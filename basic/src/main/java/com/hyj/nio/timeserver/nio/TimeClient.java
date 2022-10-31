package com.hyj.nio.timeserver.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * nio 多路复用 实现的 client
 */
public class TimeClient {

    private static String ip = "127.0.0.1";

    private static int port = 8080;

    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            try {
                ip = args[0];
                port = Integer.parseInt(args[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        new Thread(new TimeClientHandle(), "NIO-TimeClient-001").start();

    }

    static class TimeClientHandle implements Runnable {


        private Selector selector;

        private SocketChannel channel;

        private volatile boolean stop;

        public TimeClientHandle() {
            try {
                this.selector = Selector.open();
                this.channel = SocketChannel.open();
                this.channel.configureBlocking(false);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }

        }

        @Override
        public void run() {
            try {
                doConnect();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
            while (!stop){
                try{
                    this.selector.select(1000);
                    Set<SelectionKey> selectionKeys = this.selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
                    SelectionKey key = null;
                    while (keyIterator.hasNext()){
                        key = keyIterator.next();
                        keyIterator.remove();

                        try{
                            handleInput(key);
                        } catch(Exception e){
                           if(key != null){
                               key.cancel();
                               if(key.channel() != null){
                                   key.channel().close();
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
                SocketChannel sc = (SocketChannel) key.channel();
                if(key.isConnectable()){
                    if(sc.finishConnect()){
                        sc.register(this.selector,SelectionKey.OP_READ);
                        doWrite(sc);
                    } else {
                        //连接失败进程退出
                        System.exit(1);
                    }

                }

                if(key.isReadable()){
                    ByteBuffer allocate = ByteBuffer.allocate(1024);
                    int read = sc.read(allocate);
                    if(read > 0){
                        allocate.flip();
                        byte[] bytes = new byte[allocate.remaining()];
                        allocate.get(bytes);

                        String body = new String(bytes,"UTF-8");
                        System.out.println("Now is : " + body);
                        this.stop = true;
                    } else if(read < 0){
                        key.cancel();
                        sc.close();
                    } else {
                        //读到0字节 忽略
                        ;
                    }

                }

            }
        }

        private void doConnect() throws IOException {
            if (this.channel.connect(new InetSocketAddress(ip, port))) {
                this.channel.register(this.selector, SelectionKey.OP_READ);
                doWrite(this.channel);
            } else {
                this.channel.register(this.selector, SelectionKey.OP_CONNECT);
            }
        }

        private void doWrite(SocketChannel sc) throws IOException {
            byte[] bytes = "QUERY TIME ORDER".getBytes();
            ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
            byteBuffer.put(bytes);
            byteBuffer.flip();
            sc.write(byteBuffer);
            if (!byteBuffer.hasRemaining()) {
                System.out.println("Send order 2 server succeed");
            }
        }
    }
}
