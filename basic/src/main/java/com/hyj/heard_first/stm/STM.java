package com.hyj.heard_first.stm;


/**
 * software transactional memory 软件事物内存
 */
public class STM {

    private STM(){};

    //提交数据需要的全局锁
    static final Object commitLock = new Object();

    //原子化提交方法
    public static void atomic(TxnRunnable action){
        boolean committed = false;
        //如果提交失败则一直重试
        while (!committed){
            //新建事物
            STMTxn txn = new STMTxn();
            //执行业务
            action.run(txn);
            //提交事物
            committed = txn.commit();

            System.out.println("current Thrend : " + Thread.currentThread().getName() + " == " +txn.inTxnMap.toString());
        }
    }
}
