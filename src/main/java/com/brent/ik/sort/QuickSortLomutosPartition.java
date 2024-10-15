package com.brent.ik.sort;

import java.util.ArrayList;
import java.util.Random;

public class QuickSortLomutosPartition extends Sorter {
    private static ArrayList<Integer> createAuxArray(int start, int end) {
        var aux = new ArrayList<Integer>();
        while (aux.size() < end - start + 1) aux.add(null);
        return aux;
    }

    private static int pickPivot(int start, int end) {
        var pivotPoint = new Random(System.currentTimeMillis()).nextInt(start, end);
        return pivotPoint;
    }

    public ArrayList<Integer> sort(ArrayList<Integer> arr) {
        helper(arr, 0, arr.size() - 1);
        return arr;
    }

    private void helper(ArrayList<Integer> arr, int start, int end) {
        // leaf worker
        if (start >= end) return;
        // internal node worker
        int pivotPoint = pickPivot(start, end);

        int pivotIndex = partition(arr, start, end, pivotPoint);

        helper(arr, start, pivotIndex - 1);
        helper(arr, pivotIndex + 1, end);
    }

    /**
     * @param arr
     * @param start
     * @param end
     * @param pivotPoint
     * @return pivotIndex
     */
    private int partition(ArrayList<Integer> arr, int start, int end, int pivotPoint) {

        var smaller = start;

        var pivot = arr.get(pivotPoint);
        swap(arr, start, pivotPoint);

        for (int larger=start+1; larger<=end; larger++) {
            var ele = arr.get(larger);
            if(ele < pivot){
                smaller ++ ;
                swap(arr,larger,smaller);
            }
        }
        swap(arr,start,smaller);

        return smaller;
    }

}
