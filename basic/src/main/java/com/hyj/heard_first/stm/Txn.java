package com.hyj.heard_first.stm;

/**
 *  事物接口
 */
public interface Txn {

    <T> T get(TxnRef<T> ref);

    <T> void set(TxnRef<T> ref,T value);
}
