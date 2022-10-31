package com.hyj.algorithm.geek.mathematics;

import java.math.BigInteger;

public class Lesson1_1 {


    /**
     * 十进制转二进制
     * @param decimal
     * @return
     */
    public static String decimalToBinary(int decimal){
        BigInteger b = new BigInteger(String.valueOf(decimal));
        return b.toString(2);
    }

    /**
     * 二进制转十进制
     * @param binary
     * @return
     */
    public static int binaryToDecimal(String binary){
        BigInteger b = new BigInteger(binary,2);
        return Integer.parseInt(b.toString());
    }


    public static void main(String[] args) {
        int decimal = 53;
        String bianry = "110101";

        System.out.println(String.format("数字%d的二进制是：%s",decimal,decimalToBinary(decimal)));
        System.out.println(String.format("数字%s的十进制是：%d",bianry,binaryToDecimal(bianry)));

    }


}
