package com.brent.ik.sort;

import java.util.ArrayList;
import java.util.Random;

public class QuickSort extends Sorter {
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
        int pivotNumber = arr.get(pivotPoint);

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
        ArrayList<Integer> aux = createAuxArray(start, end);
        var smaller = 0;
        var pivot = arr.get(pivotPoint);
        swap(arr, start, pivotPoint);
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
        copyAuxBack(arr, start, end, aux);


        return start + smaller;
    }

    private void copyAuxBack(ArrayList<Integer> arr, int start, int end, ArrayList<Integer> aux) {
        var auxIndex = 0;
        for (int i = start; i <= end; i++, auxIndex++) {
            arr.set(i, aux.get(auxIndex));

        }
    }

}
