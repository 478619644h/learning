package com.hyj.sorted;

import java.util.Arrays;

public class CountingSort implements IArraySort {

    @Override
    public int[] sort(int[] sourceArray) {
        int[] arr = Arrays.copyOf(sourceArray,sourceArray.length);
        return CountingSort(arr);
    }

    private int[] CountingSort(int[] arr){
        int[] maxAndMinValue = getMaxAndMinValue(arr);
        int len = maxAndMinValue[0] - maxAndMinValue[1] + 1;
        int[] countingArr = new int[len];
        for (int i : arr) {
            int index = i - maxAndMinValue[1];
            countingArr[index]++;
        }
        int arrIndex = 0;
        //后向排序即 降序
        for (int i = 0,j = countingArr.length; i < j; i++) {
            int value = countingArr[i];
            while (value > 0){
                arr[arrIndex] = i + maxAndMinValue[1];
                arrIndex++;
                value--;
            }
        }
        return arr;
    }

    private int[] getMaxAndMinValue(int[] arr){
        int minValue = arr[0];
        int maxValue = arr[0];
        for (int i : arr) {
            if(i < minValue){
                minValue = i;
            }
            if(i > maxValue){
                maxValue = i;
            }
        }
        int[] result = {maxValue,minValue};
        return result;
    }

}
