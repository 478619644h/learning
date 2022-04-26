package com.hyj.heard_first.stm;

@FunctionalInterface
public interface TxnRunnable {
    void run(Txn txn);
}
