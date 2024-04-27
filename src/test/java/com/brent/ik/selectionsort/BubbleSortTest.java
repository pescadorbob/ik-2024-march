package com.brent.ik.selectionsort;

public class BubbleSortTest extends SortTest {
    @Override
    Sorter getSorter() {
        return new BubbleSort();
    }
}
