package com.brent.ik.selectionsort;

public class QuickSortTest extends SortTest {
    @Override
    Sorter getSorter() {
        return new QuickSort();
    }
}
