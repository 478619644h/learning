package com.hyj.heard_first.stm;


public class Account {

    private TxnRef<Integer> balance;

    public Account(int balance) {
        this.balance = new TxnRef<Integer>(balance);
    }

    public void transfer(Account target, int amt) {
        STM.atomic((txn) -> {
            Integer from = balance.getValue(txn);
            balance.setValue(from - amt, txn);
            Integer to = target.balance.getValue(txn);
            target.balance.setValue(to + amt, txn);
        });
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Account{");
        sb.append("balance=").append(balance.curRef.value);
        sb.append(",version=").append(balance.curRef.version);
        sb.append('}');
        return sb.toString();
    }
}
