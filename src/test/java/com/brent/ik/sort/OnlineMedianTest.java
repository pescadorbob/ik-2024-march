package com.brent.ik.sort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.core.util.Arrays.nonNullElementsIn;
import static org.junit.jupiter.params.provider.Arguments.of;

public class OnlineMedianTest {
    

    private static Stream<Arguments> shouldComputeMedianAfterEachElement() {
        var input = list();
        var expected = nonNullElementsIn(array(3, 3, 4, 6, 4));
        return Stream.of(
                of(input,expected));

    }

    private static ArrayList<Integer> list() {
        var input = new ArrayList<>(nonNullElementsIn(array(3, 4, 8, 12, 0)));
        return input;
    }

    @ParameterizedTest
    @MethodSource
    void shouldComputeMedianAfterEachElement(ArrayList<Integer> input, ArrayList<Integer> expected) {
        var actualOutput = online_median(input);
        assertThat(actualOutput).isEqualTo(expected);
    }

    static ArrayList<Integer> online_median(ArrayList<Integer> stream) {
        var response = new ArrayList<Integer>();
        var interim = new ArrayList<Integer>();
        for (int val:stream){
            interim.add(val);
            Collections.sort(interim);
            if(isOdd(interim)){
                response.add(interim.get(interim.size()/2));
            } else {
                var smaller = interim.get(interim.size()/2-1);
                var larger = interim.get(interim.size()/2);
                response.add((smaller+larger)/2);
            }
        }
        return response;
    }

    private static boolean isOdd(ArrayList<Integer> interim) {
        return interim.size() % 2 == 1;
    }
}