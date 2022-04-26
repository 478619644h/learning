package com.hyj.sorted;

import java.util.Arrays;


/**
 * 归并排序
 * 申请空间，使其大小为两个已经排序序列之和，该空间用来存放合并后的序列；
 * 设定两个指针，最初位置分别为两个已经排序序列的起始位置；
 * 比较两个指针所指向的元素，选择相对小的元素放入到合并空间，并移动指针到下一位置；
 * 重复步骤 3 直到某一指针达到序列尾；
 * 将另一序列剩下的所有元素直接复制到合并序列尾。
 */
public class MergeSort implements IArraySort {
    @Override
    public int[] sort(int[] sourceArray) {

        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        if (arr.length < 2) {
            return arr;
        }

        int mid = (int) Math.floor(arr.length / 2);

        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);

        return mergeBeaut(sort(left), sort(right));
    }

    protected int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0;
        while (left.length > 0 && right.length > 0) {
            if (left[0] >= right[0]) {
                result[i++] = left[0];
                left = Arrays.copyOfRange(left, 1, left.length);
            } else {
                result[i++] = right[0];
                right = Arrays.copyOfRange(right, 1, right.length);
            }
        }

        while (left.length > 0) {
            result[i++] = left[0];
            left = Arrays.copyOfRange(left, 1, left.length);
        }

        while (right.length > 0) {
            result[i++] = right[0];
            right = Arrays.copyOfRange(right, 1, right.length);
        }
        return result;
    }

    protected static int[] mergeBeaut(int[] a, int[] b) {
        if (a == null) a = new int[0];
        if (b == null) b = new int[0];
        int[] merged_one = new int[a.length + b.length];
        int mi = 0, ai = 0, bi = 0;
        // 轮流从两个数组中取出较小的值，放入合并后的数组中
        while (ai < a.length && bi < b.length) {
            if (a[ai] <= b[bi]) {
                merged_one[mi] = a[ai];
                ai++;
            } else {
                merged_one[mi] = b[bi];
                bi++;
            }
            mi++;
        }
        // 将某个数组内剩余的数字放入合并后的数组中
        if (ai < a.length) {
            for (int i = ai; i < a.length; i++) {
                merged_one[mi] = a[i];
                mi++;
            }
        } else {
            for (int i = bi; i < b.length; i++) {
                merged_one[mi] = b[i];
                mi++;
            }
        }
        return merged_one;
    }
}
