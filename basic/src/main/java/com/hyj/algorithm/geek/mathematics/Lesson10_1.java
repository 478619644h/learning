package com.hyj.algorithm.geek.mathematics;

import java.util.Arrays;
import java.util.HashMap;
/**
 网址：https://time.geekbang.org/column/article/76183
 实战演练：钱币组合的新问题
 和排列组合等穷举的方法相比，动态规划法关注发现某种最优解。如果一个问题无需求出所有可能的解，而是要找到满足一定条件的最优解，
 那么你就可以思考一下，是否能使用动态规划来降低求解的工作量。
 还记得之前我们提到的新版舍罕王奖赏的故事吗？国王需要支付一定数量的赏金，而宰相要列出所有可能的钱币组合，这使用了排列组合的思想。
 如果这个问题再变化为“给定总金额和可能的钱币面额，能否找出钱币数量最少的奖赏方式？”，那么我们是否就可以使用动态规划呢？
 举个例子，假设这里我们有三种面额的钱币，2 元、3 元和 7 元。为了凑满 100 元的总金额，我们有三种选择。

 另外的数学办法可以用总金额依次对最大金额纸币求余数，直到为0.商相加为答案。
 如：若 {1, 2, 3, 7}为纸币金额，对于100，所需最小纸币数：100/7=14余2; 2/2 = 1余0;则纸币数为14+1=15.
 */
public class Lesson10_1 {

    static int[] types = {2,3,7};

    static int count = 0;

    //带备忘录的递归解法
    static HashMap<Integer,int[]> re = new HashMap<>();

    public static int getMin(int total){

        int length = total > 7 ? total:7;
        int[] totalArr = new int[length+1];

        totalArr[0] = 0;
        totalArr[1] = 0;
        totalArr[2] = 1;
        totalArr[3] = 1;
        totalArr[4] = 2;
        totalArr[7] = 1;
        if(totalArr[total] > 0){
            return totalArr[total];
        }

        if(total > 4){
            count++;
            int[] t = new int[types.length];
            if(re.containsKey(total)){
                t = re.get(total);
                System.out.print(Arrays.toString(t));
                System.out.println(" cached current total:" + total);
            } else {
                for (int i = 0; i < types.length; i++) {
                    t[i] = total - types[i] > 1 ? getMin(total-types[i]) + 1 : Integer.MAX_VALUE;
                }
                re.put(total,t);
                System.out.print(Arrays.toString(t));
                System.out.println(" current total:" + total);
            }
            int min = t[0];
            for (int i = 1; i < t.length; i++) {
                min = Math.min(min,t[i]);
            }
            totalArr[total] = min;
        }
        return totalArr[total];
    }


    public static void main(String[] args) {
        System.out.println(getMin(100));
        System.out.println(count);
    }


}

/**
 中国程序员的最大阻碍是语言障碍，英语不好，无法和世界各地的人交流技术，坐井观天的人很多。
 第二个严重的问题就是学习能力不强而且没有毅力，很容易放弃，不肯花时间深入思考问题和钻研，大多思考如何能少加班，如何能赚更多，
 如何在工作中偷懒等等。
 第三个问题就是好高骛远不能脚踏实地，很多人刚毕业就想要很多钱，换一份工作就想涨很多钱，但是能力不够，基础不扎实，有些连在简
 历中写的技术都说不清楚。曾经我是他们中的一员，但是我想改变去，不想继续平庸下去，所以，我来了，加油，坚持坚持再坚持!!!
 */