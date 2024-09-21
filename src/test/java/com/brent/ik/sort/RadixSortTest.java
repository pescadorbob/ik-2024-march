package com.brent.ik.sort;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RadixSortTest {

    @Test
    void radix_sort() {
        var input = new ArrayList(Arrays.asList(5, 8, 3, 9, 4, 1, 7));
        var expectedOutput = Arrays.asList(1,3,4,5,7,8,9);
        var actualOutput = RadixSort.radix_sort(input);
        assertThat(actualOutput).isEqualTo(expectedOutput);
    }
    /*
    input
    {
"arr": [5, 8, 3, 9, 4, 1, 7]
}
output
[1, 3, 4, 5, 7, 8, 9]

     */
}