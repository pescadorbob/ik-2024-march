package com.brent.ik.sort;

import java.util.ArrayList;

public abstract class Sorter {
    public static void swap(ArrayList<Integer> items, int left, int right) {
        int temp = items.get(left);
        items.set(left, items.get(right));
        items.set(right, temp);
    }

    abstract ArrayList<Integer> sort(ArrayList<Integer> items);
}
