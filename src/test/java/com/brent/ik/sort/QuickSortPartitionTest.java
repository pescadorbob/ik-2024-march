package com.brent.ik.sort;

import java.util.ArrayList;
import java.util.Random;

import static java.util.Collections.swap;


public class QuickSortPartitionTest extends SortTest implements Sorter {


    public ArrayList<Integer> sort(ArrayList<Integer> arr) {
        helper(arr, 0, arr.size() - 1);
        return arr;
    }

    private void helper(ArrayList<Integer> arr, int start, int end) {
        if (start >= end) return;

        int pivot = partition(arr, start, end);
        helper(arr, start, pivot - 1);
        helper(arr, pivot + 1, end);
    }

    private int partition(ArrayList<Integer> arr, int start, int end) {
        int smaller = start;

        int pivotIndex = new Random(System.currentTimeMillis()).nextInt(start, end);
        int pivot = arr.get(pivotIndex);
        swap(arr, pivotIndex, start);
        for (int larger = start + 1; larger <= end; larger++) {
            if (arr.get(larger) < pivot) {
                smaller++;
                swap(arr, larger, smaller);
            }
        }
        swap(arr, start, smaller);
        return smaller;
    }

    @Override
    Sorter getSorter() {
        return this;
    }
}
