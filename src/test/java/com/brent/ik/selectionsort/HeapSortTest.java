package com.brent.ik.selectionsort;

public class HeapSortTest extends SortTest {
    @Override
    Sorter getSorter() {
        return new HeapSort();
    }
}
