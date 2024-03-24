package com.brent.ik.selectionsort;

public class SelectionSortTest extends SortTest{
    @Override
    Sorter getSorter() {
        return new SelectionSort();
    }
}
