package com.hyj.algorithm.geek.mathematics;

import java.math.BigInteger;

public class Lesson1_2 {


    /**
     * 左移位
     * @param num  等待移位操作的十进制数
     * @param m  向左移位的位数
     * @return
     */
    public static int leftShift(int num,int m){
        return num << m;
    }

    /**
     * 逻辑右移
     * @param num
     * @param m
     * @return
     */
    public static int rightShiftOfLogic(int num,int m){
        return num >>> m;
    }

    /**
     * 算数右移
     * @param num
     * @param m
     * @return
     */
    public static int rightShiftOfMath(int num,int m){
        return num >> m;
    }

    public static void main(String[] args) {
        int num = -8;
        int m = 2;

        System.out.println("leftShift : " + leftShift(num,m));

        BigInteger b = new BigInteger(String.valueOf(rightShiftOfLogic(num,m)));
        System.out.println("rightShiftOfLogic : " + new BigInteger("-8").toString(2) );
        System.out.println("rightShiftOfLogic : " + b.toString(2) );
        System.out.println("rightShiftOfMath : " + rightShiftOfMath(num,m));

    }

}
