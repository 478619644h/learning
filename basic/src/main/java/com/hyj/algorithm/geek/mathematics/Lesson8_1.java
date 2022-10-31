package com.hyj.algorithm.geek.mathematics;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * n 个元素里取出 m 个的组合，可能性数量就是 n 个里取 m 个的排列数量，除以 m 个全排列的数量，也就是 (n! / (n-m)!) / m!
 * 对于全组合而言，可能性为 2^n 种。例如，当 n=3 的时候，全组合包括了 8 种情况。
 * 全组合就是{空集, {1}, {2}, {3}, {1, 2}, {1,3} {2, 3}, {1, 2, 3}}
 */
public class Lesson8_1 {

    static int count = 0;


    public static void combination(ArrayList<String> eles, ArrayList<String> result,int n){

        if(result.size() == n){
            System.out.println(result);
            count++;
            return;
        }

        for (int i = 0; i < eles.size(); i++) {

             ArrayList<String> current_result = (ArrayList<String>)result.clone();
             current_result.add(eles.get(i));

            ArrayList<String> current_eles = new ArrayList<>(eles.subList(i+1,eles.size()));
            //current_eles.remove(i);

            combination(current_eles,current_result,n);

        }

    }

    public static void main(String[] args) {
        final ArrayList<String> strings = new ArrayList<String>(Arrays.asList("a","b","c","d","e","f","g","h","i","j"));
        combination(strings,new ArrayList<>(),6);
        System.out.println(count);
        System.out.println(10*9*8*7/24);
    }


}
