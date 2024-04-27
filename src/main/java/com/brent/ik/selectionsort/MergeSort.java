package com.brent.ik.selectionsort;

import java.util.ArrayList;


public class MergeSort extends Sorter {
    public ArrayList<Integer> sort(ArrayList<Integer> arr) {
        helper(arr, 0, arr.size() - 1);

        return arr;
    }

    private void helper(ArrayList<Integer> arr, int start, int end) {
        // leaf worker
        if (start == end) return; // all done here
        // internal node worker
        var mid = (start + end) / 2;
        helper(arr, start, mid);
        helper(arr, mid + 1, end);
        // merge the two sorted halves
        var left = start;
        var right = mid + 1;

        var aux = new ArrayList<Integer>(end - start + 1);
        while (left <= mid && right <= end) {

            if (arr.get(left) <= arr.get(right)) {
                aux.add(arr.get(left)); // move left one down
                left++;
            } else { // right is smaller
                // move right one down
                aux.add(arr.get(right));
                right++;
            }

        }
        // copy remaining left ones
        while (left <= mid) {
            aux.add(arr.get(left++));
        }
        // copy remaining right ones
        while (right <= end) {
            aux.add(arr.get(right++));
        }
        //copy aux back to original
        var copyAuxIndex = 0;
        var originalIndex = start;
        while (originalIndex <= end) {
            arr.set(originalIndex++, aux.get(copyAuxIndex++));
        }
    }


}
