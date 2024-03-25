package com.brent.ik.selectionsort;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class SortTest {


    @ParameterizedTest
    @MethodSource("provideArrays")
    public void testSelectionSortSortArrayOfTenItems(ArrayList<Integer> items, ArrayList<Integer> expectedItems) {

        var actual = getSorter().sort(items);
        assertThat(actual).isEqualTo(expectedItems);
    }

    abstract Sorter getSorter();

    private static Stream<Arguments> provideArrays() {
        return Stream.of(
                Arguments.of(list(10, 9, 4, 5, 6, 7),
                        list(4, 5, 6, 7, 9, 10)),
                Arguments.of(list(1, 2, 3, 0, -1, -2),
                        list(-2, -1, 0, 1, 2, 3)),
                Arguments.of(list(-1, 2, 2, -1),
                        list(-1, -1, 2, 2)),
                Arguments.of(list(5, 8, 3, 9, 4, 1, 7),
                        list(1, 3, 4, 5, 7, 8, 9)),
                Arguments.of(list(5, 6, 3, 9, 2, 4, 1, 0),
                        list(0, 1, 2, 3, 4, 5, 6, 9)),
                Arguments.of(list(-1, 0, 5, 6, 3, 9, 2, 4, 1, 0),
                        list(-1, 0, 0, 1, 2, 3, 4, 5, 6, 9))
//                ,Arguments.of(load("input013.txt"))
        );
    }



    private static ArrayList<Integer> list(Integer... array) {
        return new ArrayList<>(java.util.Arrays.stream(array).toList());
    }
}
