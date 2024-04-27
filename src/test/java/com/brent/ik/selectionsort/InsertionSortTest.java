package com.brent.ik.selectionsort;

public class InsertionSortTest extends SortTest {
    @Override
    Sorter getSorter() {
        return new InsertionSort();
    }
}
