package com.brent.ik.sort;

public class InsertionSortTest extends SortTest {
    @Override
    Sorter getSorter() {
        return new InsertionSort();
    }
}
