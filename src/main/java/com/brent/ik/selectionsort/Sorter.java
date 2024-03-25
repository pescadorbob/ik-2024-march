package com.brent.ik.selectionsort;

import java.util.ArrayList;

public abstract class Sorter {
    abstract ArrayList<Integer> sort(ArrayList<Integer> items);

    public static void swap(ArrayList<Integer> items, int left, int right) {
        int temp = items.get(left);
        items.set(left, items.get(right));
        items.set(right, temp);
    }
}
