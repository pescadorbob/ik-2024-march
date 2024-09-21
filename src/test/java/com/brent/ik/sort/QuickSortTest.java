package com.brent.ik.sort;

public class QuickSortTest extends SortTest {
    @Override
    Sorter getSorter() {
        return new QuickSort();
    }
}
