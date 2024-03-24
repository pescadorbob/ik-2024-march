package com.brent.ik.selectionsort;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import com.fasterxml.jackson.databind.ObjectMapper; // Import Jackson library

import java.io.File;
import java.io.IOException;
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

    abstract Sorter getSorter() ;

    private static Stream<Arguments> provideArrays() {
        return Stream.of(
                Arguments.of(list(5, 6, 3, 9, 2, 4, 1, 0),
                        list(0, 1, 2, 3, 4, 5, 6, 9)),
                Arguments.of(list(-1,0,5, 6, 3, 9, 2, 4, 1, 0),
                        list(-1,0,0, 1, 2, 3, 4, 5, 6, 9))
//                ,Arguments.of(load("input013.txt"))
                );
    }

    private static ArrayList<Integer> load(String resourceFileName) {
        try {
            // Read the JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File("input013.txt"); // Replace with your actual file path
            Integer[] dataArray = objectMapper.readValue(jsonFile, Integer[].class);
            var retVal = new ArrayList<Integer>();
            for (Integer integer : dataArray) {
                retVal.add(integer);
            }
            return retVal;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ArrayList<Integer> list(Integer... array) {
        return new ArrayList<Integer>(java.util.Arrays.stream(array).toList());
    }
}
