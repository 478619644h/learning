package com.hyj.heard_first.stm;

import java.math.BigInteger;

/**
 * 支持事物的引用
 * @param <T>
 */
public class TxnRef<T> {

    //当前数据 带版本号
    volatile VersionedRef curRef;

    public TxnRef(T value){
        this.curRef = new VersionedRef(value,0l);
    }

    /**
     * 获取当前事物中的数据
     * @param txn
     * @return
     */
    public T getValue(Txn txn){
        return txn.get(this);
    }

    /**
     * 在当前事物中设置数据
     * @param value
     * @param txn
     */
    public void setValue(T value,Txn txn){
        txn.set(this,value);
    }

}
