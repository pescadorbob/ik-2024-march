package com.brent.ik.sort;

import java.util.ArrayList;
import java.util.Random;

public class QuickSortLomutosPartition extends Sorter {

    private static int pickPivot(int start, int end) {
        var pivotPoint = new Random(System.currentTimeMillis()).nextInt(start, end);
        return pivotPoint;
    }

    public ArrayList<Integer> sort(ArrayList<Integer> arr) {


        helper(arr, 0, arr.size() - 1);
        return arr;
    }

    private void helper(ArrayList<Integer> arr, int start, int end) {
        if (start >= end) return;
        int pivot = pickPivot(start, end);

        int pivotPoint = partition(arr, start, end, pivot);

        helper(arr, start, pivotPoint - 1);
        helper(arr, pivotPoint + 1, end);
    }

    private int partition(ArrayList<Integer> arr, int start, int end, int pivot) {
        swap(arr,start,pivot);

        int smaller = start;
        for(int larger = start;larger<=end;larger++){
            var ele = arr.get(larger);
            if(ele<arr.get(start)){
                smaller ++;
                swap(arr,smaller,larger);
            }
        }
        swap(arr,smaller,start);
        return smaller;
    }

}
