package com.hyj.algorithm.geek.mathematics;

import java.util.ArrayList;
import java.util.List;

public class Lesson5_1 {

    static int count = 0;

    static int[] rewards = {1,2,5,10};

    /**
     * 列出所有可能的奖赏方式
     * 假设有四种面额的钱币，1 元、2 元、5 元和 10 元，而您一共给我 10 元，
     * 那您可以奖赏我 1 张 10 元，或者 10 张 1 元，或者 5 张 1 元外加 1 张 5 元等等。
     * 如果考虑每次奖赏的金额和先后顺序，那么最终一共有多少种不同的奖赏方式呢？
     * @param totalReward
     * @param result
     */
    static void get(long totalReward, List<Long> result){
        if(totalReward == 0){
            System.out.println(result);
            count++;
            return;
        } else if(totalReward < 0){
            return;
        } else {
            for (int i = 0,j = rewards.length; i < j; i++){
                List currentResult = new ArrayList(result);
                currentResult.add(rewards[i]);
                get(totalReward - rewards[i],currentResult);
            }

        }
    }

    /**
     * 一个整数可以被分解为多个整数的乘积，例如，6 可以分解为 2x3。请使用递归编程的方法，
     * 为给定的整数 n，找到所有可能的分解（1 在解中最多只能出现 1 次）。
     * 例如，输入 8，输出是可以是 1x8, 8x1, 2x4, 4x2, 1x2x2x2, 1x2x4, ……
     * @param totalReward
     * @param result
     */
    static void recusion(long totalReward, List<Long> result){

        if(totalReward == 1){
            System.out.println(result);
            return;
        } else {
            for (long i = 1;i<=totalReward;i++){
                // 解决 n/1 = n 无限递归
                if ((i == 1) && result.contains(1L)) continue;
                //不能整除的不需要
                if(totalReward%i !=0){
                    continue;
                }
                List currentResult = new ArrayList<Long>(result);
                currentResult.add(i);
                // n/1 = n
                recusion(totalReward/i,currentResult);
            }
        }

    }


    public static void main(String[] args) {
        long totalReward = 10;
        //get(totalReward,new ArrayList<Long>());
        //System.out.println(count);
        recusion(8,new ArrayList<Long>());
    }

}
