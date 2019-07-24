package com.hyj.sorted;

import java.util.Arrays;

/**
 * 直接插入排序法
 */
public class InsetSort {

    public static void main(String[] args) {
        int[] a = {3,1,4,6,23,6,72,9};
        insert_sort(a);
        for(int i=0;i<a.length;i++){
            System.out.println(a[i]);
        }
    }

    public static void pa(int[] a){
            System.out.println(Arrays.toString(a));
    }

    public static void insert_sort(int[] a){
        int n = a.length;
        int i,j;
        for(i = 1;i < n; i++){
            pa(a);
            int temp = a[i];
            for(j=i-1;j>=0&&a[j] > temp;j--){
                a[j+1] = a[j];
            }
            a[j+1] = temp;
        }
    }
}
