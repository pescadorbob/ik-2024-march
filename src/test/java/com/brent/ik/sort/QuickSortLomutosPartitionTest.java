package com.brent.ik.sort;

public class QuickSortLomutosPartitionTest extends SortTest {
    @Override
    Sorter getSorter() {
        return new QuickSortLomutosPartition();
    }
}
