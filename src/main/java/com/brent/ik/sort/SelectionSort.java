package com.brent.ik.sort;

import java.util.ArrayList;

import static java.util.Collections.swap;

public class SelectionSort implements Sorter {
    public ArrayList sort(ArrayList<Integer> arr) {
        for (int i = 0; i <= arr.size() - 1; i++) {
            int minvalue = arr.get(i);
            int minindex = i;
            for (int red = i + 1; red <= arr.size() - 1; red++) {
                if (arr.get(red) < arr.get(minindex)) {

                    minindex = red;
                }
            }
            swap(arr, i, minindex);
        }
        return arr;
    }

}
