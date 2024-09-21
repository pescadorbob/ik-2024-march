package com.brent.ik.sort;

public class BubbleSortTest extends SortTest {
    @Override
    Sorter getSorter() {
        return new BubbleSort();
    }
}
