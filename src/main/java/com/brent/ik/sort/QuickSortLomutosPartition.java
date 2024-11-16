package com.brent.ik.sort;

import java.util.ArrayList;
import java.util.Random;

import static java.util.Collections.swap;


public class QuickSortLomutosPartition extends Sorter {


    public ArrayList<Integer> sort(ArrayList<Integer> arr) {
        helper(arr,0,arr.size()-1);
        return arr;
    }
    private void helper(ArrayList<Integer> arr,int start, int end){
        // leaf node worker
        if(start>=end) return;
        int pivot = lomutosPartition(arr, start, end);
        helper(arr,start,pivot-1);
        helper(arr,pivot+1,end);
    }

    public static int lomutosPartition(ArrayList<Integer> arr, int start, int end) {
        int pivotIndex = pickPivot(start, end);
        int pivot = arr.get(pivotIndex);
        int smaller = start;
        int larger = start ;
        swap(arr, pivotIndex, start);
        while (larger <= end) {
            if (arr.get(larger) < pivot) {
                smaller++;
                swap(arr, smaller, larger);
            }
            larger++;

        }
        swap(arr, start, smaller);
        return smaller;
    }

    private static int pickPivot(int from,int to){
        return new Random(System.currentTimeMillis()).nextInt(from,to);
    }



}
