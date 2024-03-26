package com.brent.ik.merge;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class MergeTest2 {

    @ParameterizedTest
    @MethodSource("provideArrays")
    void test_merge_one_into_another(ArrayList<Integer> first, ArrayList<Integer> second, ArrayList<Integer> expected){
        var actual = merge_one_into_another(first,second);
        assertThat(actual).isEqualTo(expected);
    }
    private static Stream<Arguments> provideArrays() {
        return Stream.of(
                Arguments.of(list(1,3,5),
                        list(2,4,6,0,0,0),list(1,2,3,4,5,6))
        );
    }

    private static ArrayList<Integer> list(Integer... array) {
        return new ArrayList<>(java.util.Arrays.stream(array).toList());
    }

    static ArrayList<Integer> merge_one_into_another(ArrayList<Integer> first, ArrayList<Integer> second) {
        int k = second.size() - 1;

        int i = first.size() - 1;
        int j =i; // same number of entries in both array are populated //but in the second array-- last non zero in second aray


        while (k >= 0 && i >= 0) {

            if (j >= 0 && second.get(j) >= first.get(i)) {
                second.set(k, second.get(j));
                j--;
            } else {
                second.set(k, first.get(i));
                i--;
            }
            k--;
        }

        return second;
    }
}

