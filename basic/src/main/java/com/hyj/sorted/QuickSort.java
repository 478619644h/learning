package com.hyj.sorted;

import java.util.Arrays;


/**
 * 快速排序
 * 从数列中挑出一个元素，称为 “基准”（pivot）;
 * 重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面（相同的数可以到任一边）。
 * 在这个分区退出之后，该基准就处于数列的中间位置。这个称为分区（partition）操作；
 * 递归地（recursive）把小于基准值元素的子数列和大于基准值元素的子数列排序；
 */
public class QuickSort implements IArraySort {
    @Override
    public int[] sort(int[] sourceArray) {
        int [] arr = Arrays.copyOf(sourceArray,sourceArray.length);
        return quickSort(arr,0,arr.length-1);
    }

    public int[] quickSort(int[] arr,int left,int right){
        if(left < right){
            int partitionIndex = partition(arr,left,right);
            quickSort(arr,left,partitionIndex - 1);
            quickSort(arr,partitionIndex + 1,right);
        }
        return arr;
    }


    public int partition(int[] arr,int left,int right){
        int pivot = left;
        int index = pivot + 1;
        for (int i = index; i <= right; i++){
            if(arr[i] > arr[pivot]){
                swap(arr,i,index);
                index++;
            }
        }
        swap(arr,pivot,index-1);
        return index-1;
    }


    public void swap(int[] arr,int left,int right){
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }

    public static void main(String[] args) {
        Integer i =1000;
        Integer index = i;
        i =10;
        System.out.println(index);

    }

}
