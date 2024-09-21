package com.brent.ik.sort;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class RadixSortTest {

    private static Stream<Arguments> provideArrays() {
        return Stream.of(
                Arguments.of(list(5,8,3,9,4,1,7), list(1,3,4,5,7,8,9)),
                Arguments.of(list(5,8,3,9,4,11,7), list(3,4,5,7,8,9,11))
        );
    }
    private static ArrayList<Integer> list(Integer ... nums){
        return new ArrayList<>(asList(nums));
    }
    @ParameterizedTest
    @MethodSource("provideArrays")

    void radix_sort(ArrayList input,ArrayList expectedOutput) {
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