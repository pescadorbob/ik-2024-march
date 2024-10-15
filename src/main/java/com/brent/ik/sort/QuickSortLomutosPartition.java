package com.brent.ik.sort;

import java.util.ArrayList;
import java.util.Random;


public class QuickSortLomutosPartition extends Sorter {


    public ArrayList<Integer> sort(ArrayList<Integer> arr) {
        helper(arr, 0, arr.size()-1);
        return arr;
    }

    private static void helper(ArrayList<Integer> arr, int start, int end) {
        if (start >= end) return; // leaf node

        int pivotPoint = pickPivot(start, end);

        int partitionIndex = partition(arr, start, end, pivotPoint);

        helper(arr, start, partitionIndex - 1);
        helper(arr, partitionIndex + 1, end);

    }

    private static int partition(ArrayList<Integer> arr, int start, int end, int pivot) {

        int smaller = start;
        swap(arr, start, pivot);

        for (int larger = start; larger <= end; larger++) {
            int ele = arr.get(larger);
            if (ele < arr.get(start)) {
                smaller++;
                swap(arr, smaller, larger);
            }
        }
        swap(arr, start, smaller);
        return smaller;

    }

    private static int pickPivot(int start, int end) {
        return new Random(System.currentTimeMillis()).nextInt(start, end);
    }

}
