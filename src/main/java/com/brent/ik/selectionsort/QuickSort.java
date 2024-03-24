package com.brent.ik.selectionsort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class QuickSort extends Sorter {
    public ArrayList<Integer> sort(ArrayList<Integer> arr) {
        helper(arr, 0, arr.size() - 1);
        return arr;
    }

    private void helper(ArrayList<Integer> arr, int start, int end) {
        System.out.println(String.format("Helper %s %d:%d", arr.toString(),start,end));
        // leaf worker
        if (start >= end) return;
        // internal node worker
        var aux = new ArrayList<Integer>();
        while (aux.size() < end - start + 1) aux.add(null);
        var smaller = 0;
        var pivotPoint = new Random(System.currentTimeMillis()).nextInt(start,end);
        var pivot = arr.get(pivotPoint);
        swap(arr,start,pivotPoint);
        var larger = aux.size() - 1;
        for (int i = start + 1; i <= end; i++) {
            var ele = arr.get(i);
            if (ele > pivot) {
                aux.set(larger, ele);
                larger--;
            } else {
                aux.set(smaller, ele);
                smaller++;
            }
        }
        aux.set(smaller, pivot);
        System.out.println(String.format("Pivot:%d aux array:%s", pivot, aux.toString()));
        copyArrayBack(arr, start, end, aux);
        System.out.println(String.format("Pivot:%d After Partitioning:%s", pivot, arr.toString()));

        helper(arr, start, start + smaller - 1);
        helper(arr, start + smaller + 1, end);
    }

    private void copyArrayBack(ArrayList<Integer> arr, int start, int end, ArrayList<Integer> aux) {
        var auxIndex = 0;
        for (int i = start; i <= end; i++, auxIndex++) {
            arr.set(i, aux.get(auxIndex));

        }
    }

}
