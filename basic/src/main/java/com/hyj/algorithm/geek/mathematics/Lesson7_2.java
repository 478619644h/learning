package com.hyj.algorithm.geek.mathematics;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * n 组成元素的种类 m 排列的位数 全排列 n==m
 * 不重复排列 n!/(n-m)!   （1≤m≤n）
 * 不重复全排列 n！
 * 重复排列 n＾m
 */
public class Lesson7_2 {

    static String[] eles = {"a","b","c","d","e"};

    static int a = 0;

    /**
     * 假设有一个 4 位字母密码，每位密码是 a～e 之间的小写字母。你能否编写一段代码，来暴力破解该密码？
     * （提示：根据可重复排列的规律，生成所有可能的 4 位密码。）
     * @param n
     * @param eles
     * @param result
     */
    public static void getPassword(int n, ArrayList<String> eles,ArrayList<String> result){
        if(result.size() == 4){
            System.out.println(result);
            a = a + 1;
            return;
        }

        for (String ele : eles) {


            ArrayList<String> current_result = (ArrayList<String>) result.clone();
            current_result.add(ele);
            // 放开注释 生成不可重复的密码
            ArrayList<String> current_eles = (ArrayList<String>) eles.clone();

            current_eles.remove(ele);


            getPassword(n,current_eles,current_result);

        }

    }

    public static void main(String[] args) {
        getPassword(4, new ArrayList<String>(Arrays.asList(eles)),new ArrayList<String>());
        System.out.println(a);
    }

}
