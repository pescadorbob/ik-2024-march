package com.brent.ik.selectionsort;

public class MergeSortTest extends SortTest{
    @Override
    Sorter getSorter() {
        return new MergeSort();
    }
}
