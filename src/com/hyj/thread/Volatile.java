package com.hyj.thread;

public class Volatile {
    public static void main(String[] args) {
        Thread threads[]  =   new  Thread[ 3 ];
        for  ( int  i  =   0 ; i  <  threads.length; i ++ ) {
            threads[i]  =   new  ThreadPrint((char)('A' + i), 10*threads.length + 3 );
        }
        for  ( int  i  =   0 ; i  <  threads.length; i ++ ) {
            threads[i].start();
        }
    }
}
class ThreadPrint extends  Thread {
    private char currentName;
    private int printNum;
    public ThreadPrint(char currentName,int printNum) {
        this.currentName = currentName;
        this.printNum = printNum;
    }
    static volatile int  n  =   3 ;
    public void run() {
        while (n<printNum) {
//                System.out.println("current Name is : " +  currentName + "current n is: " + n);
            if(currentName ==  (char)('A' + n%3)) {
                System.out.print(currentName);
                n++;
            }
        }
    }
}