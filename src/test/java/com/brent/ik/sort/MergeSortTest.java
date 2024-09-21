package com.brent.ik.sort;

public class MergeSortTest extends SortTest {
    @Override
    Sorter getSorter() {
        return new MergeSort();
    }
}
