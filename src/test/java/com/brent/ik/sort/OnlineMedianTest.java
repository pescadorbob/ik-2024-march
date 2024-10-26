package com.brent.ik.sort;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.core.util.Arrays.nonNullElementsIn;

public class OnlineMedianTest {

    @Test
    void shouldComputeMedianAfterEachElement() {
        var input = new ArrayList<>(nonNullElementsIn(array(3, 4, 8, 12, 0)));
        var expectedOutput = nonNullElementsIn(array(3, 3, 4, 6, 4));
        var actualOutput = online_median(input);
        assertThat(actualOutput).isEqualTo(expectedOutput);
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