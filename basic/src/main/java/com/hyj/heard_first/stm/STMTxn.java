package com.hyj.heard_first.stm;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * stm事物实现
 */
public class STMTxn implements Txn{

    //事物ID生成器
    private static AtomicLong txnReq = new AtomicLong(0);

    //当前事物所有相关数据
    public Map<TxnRef,VersionedRef> inTxnMap = new HashMap<>();

    //当前事物所有需要修改的数据
    private Map<TxnRef,Object> writeMap = new HashMap<>();

    //当前事物id
    private long txnId;

    //自动生成当前事物的ID
    STMTxn(){
        txnId = txnReq.incrementAndGet();
    }

    //获取当前事物中的数据
    @Override
    public <T> T get(TxnRef<T> ref) {
        //将需要读取的数据加入到inTxnMap
        if(!inTxnMap.containsKey(ref)){
            inTxnMap.put(ref,ref.curRef);
        }
        return (T) inTxnMap.get(ref).value;
    }

    //在当前事物中修改数据
    @Override
    public <T> void set(TxnRef<T> ref, T value) {
        //将需要修改的数据加入到inTxnMap
        if(!inTxnMap.containsKey(ref)){
            inTxnMap.put(ref,ref.curRef);
        }
        writeMap.put(ref,value);
    }

    boolean commit(){

        synchronized (STM.commitLock){
            boolean isValid = true;
            //校验所有数据是否发生过变化
            for (Map.Entry<TxnRef,VersionedRef> entry : inTxnMap.entrySet()){
                VersionedRef curRef = entry.getKey().curRef;
                VersionedRef readRef = entry.getValue();
                //通过版本号验证数据是否发生过变化
                if(curRef.version != readRef.version){
                    isValid = false;
                    break;
                }
            }

            if(isValid){
                writeMap.forEach((k,v) -> {
                    k.curRef = new VersionedRef(v,txnId);
                });
            }

            return isValid;
        }
    }
}
