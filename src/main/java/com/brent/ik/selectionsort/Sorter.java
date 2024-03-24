package com.brent.ik.selectionsort;

import java.util.ArrayList;

public abstract class Sorter {
    abstract ArrayList<Integer> sort(ArrayList<Integer> items);

    void swap(ArrayList<Integer> items, int i, int minindex) {
        int temp = items.get(i);
        items.set(i,items.get(minindex));
        items.set(minindex,temp);
        }
}
