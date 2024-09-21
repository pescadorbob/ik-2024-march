package com.brent.ik.sort;

public class SelectionSortTest extends SortTest {
    @Override
    Sorter getSorter() {
        return new SelectionSort();
    }
}
