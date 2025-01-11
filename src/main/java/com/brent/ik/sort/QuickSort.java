package com.brent.ik.sort;

import java.util.ArrayList;
import java.util.Random;

import static java.util.Collections.swap;

public class QuickSort implements Sorter {

    public ArrayList<Integer> sort(ArrayList<Integer> arr) {
        helper(arr, 0, arr.size() - 1);
        return arr;
    }
    private static void helper(ArrayList<Integer> arr,int start, int end){
        // base case
        if(start>=end) return;

        int pivot = partition(arr,start,end);
        helper(arr,start,pivot-1);
        helper(arr,pivot+1,end);
    }

    private static int partition(ArrayList<Integer> arr, int start, int end) {
        int pivotIndex = new Random(System.currentTimeMillis()).nextInt(start,end);
        int smaller = start;
        // lomutos
        swap(arr,start,pivotIndex);

        for(int larger = start+1;larger<=end;larger++){
            if(arr.get(larger)<arr.get(start)){
                smaller++;
                swap(arr,smaller,larger);
            }
        }
        swap(arr,start,smaller);
        return smaller;
    }

}
