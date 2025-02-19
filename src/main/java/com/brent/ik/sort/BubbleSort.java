package com.brent.ik.sort;

import java.util.ArrayList;

import static java.util.Collections.swap;


public class BubbleSort implements Sorter {
    public ArrayList<Integer> sort(ArrayList<Integer> arr) {
        for (int i = 0; i <= arr.size() - 1; i++) {
            for (int red = arr.size() - 1; red >= i + 1; red--) {
                if (arr.get(red - 1) > arr.get(red)) {
                    swap(arr, red - 1, red);
                }
            }
        }
        return arr;
    }


}
