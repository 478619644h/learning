package com.hyj.algorithm;

import java.util.Stack;

public class MyStack2 {

    private Stack<Integer> stackData;
    private Stack<Integer> stackMin;

    public MyStack2() {
        this.stackData = new Stack<Integer>();
        this.stackMin = new Stack<Integer>();
    }

    public void push(int newNum){
        if (this.stackMin.isEmpty()){
            this.stackMin.push(newNum);
        } else if(newNum < this.getMin()){
            this.stackMin.push(newNum);
        } else {
            this.stackMin.push(this.stackMin.peek());
        }
        this.stackData.push(newNum);
    }

    public int pop(){
        if(this.stackData.isEmpty()){
            throw new RuntimeException("Your stack is empty");
        }
        this.stackMin.pop();
        return this.stackData.pop();
    }

    public int getMin(){
        if(this.stackMin.isEmpty()){
            throw new RuntimeException("Your stack is empty");
        }
        return this.stackMin.peek();
    }

    public static void main(String[] args) {
        int n = 10;
        System.out.println(50 - (n++) + (--n));
    }

}
