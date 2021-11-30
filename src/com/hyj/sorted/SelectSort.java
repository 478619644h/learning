package com.hyj.sorted;

import java.util.Arrays;

/**
 * 首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置
 *
 * 再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。
 *
 * 重复第二步，直到所有元素均排序完毕。
 */
public class SelectSort implements IArraySort {
    @Override
    public int[] sort(int[] sourceArray) {
        int[] arr = Arrays.copyOf(sourceArray,sourceArray.length);

        for(int i = 0; i < arr.length - 1; i++){
            int maxIndex = i;
            //在未排序的部分中选择最小（大）的元素
            for(int j = i; j < arr.length; j++){
                if(arr[j] > arr[maxIndex]){
                    maxIndex = j;
                }
            }
            //避免多次交换
            //对选择出来的元素进行交换
            if(maxIndex != i){
                int temp = arr[maxIndex];
                arr[maxIndex] = arr[i];
                arr[i] = temp;
            }
        }

        return arr;
    }
}
