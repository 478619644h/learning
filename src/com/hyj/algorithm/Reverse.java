package com.hyj.algorithm;

import java.util.Stack;

/**
 * 通过递归实现栈元素反转
 */
public class Reverse {


    public static int getLastVal(Stack<Integer> stack){
        int result = stack.pop();
        if(stack.isEmpty()){
            return result;
        } else {
            int last = getLastVal(stack);
            stack.push(result);
            return  last;
        }

    }

    public static void reverse(Stack<Integer> stack){
        if(stack.isEmpty()){
            return;
        }
        int last = getLastVal(stack);
        reverse(stack);
        stack.push(last);
    }

    private static Stack<Integer> test = new Stack<>();

    public static void main(String[] args) {
        test.push(1);
        test.push(2);
        test.push(3);
        System.out.println(test.toString());
        reverse(test);
        System.out.println(test.toString());
    }

}
