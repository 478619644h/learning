package com.hyj.heard_first.stm;


public class TestMain {

    public static void main(String[] args) throws Exception {
        Account from = new Account(1000);
        Account to = new Account(800);
        final Thread threadA = new Thread("A"){
            @Override
            public void run() {
                from.transfer(to,200);
            }
        };
        threadA.start();

        final Thread threadB = new Thread("B"){
            @Override
            public void run() {
                from.transfer(to,200);
            }
        };
        threadB.start();

        Thread.sleep(1000);
    }
}
