package com.brent.ik.sort;

public class HeapSortTest extends SortTest {
    @Override
    Sorter getSorter() {
        return new HeapSort();
    }
}
