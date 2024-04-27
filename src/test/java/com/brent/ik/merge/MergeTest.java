package com.brent.ik.merge;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class MergeTest {

    private static Stream<Arguments> provideArrays() {
        return Stream.of(
                Arguments.of(list(1, 3, 5),
                        list(2, 4, 6, 0, 0, 0), list(1, 2, 3, 4, 5, 6)),
                Arguments.of(list(10, 10, 10),
                        list(10, 10, 10, 0, 0, 0), list(10, 10, 10, 10, 10, 10))
        );
    }

    private static ArrayList<Integer> list(Integer... array) {
        return new ArrayList<>(java.util.Arrays.stream(array).toList());
    }

    static ArrayList<Integer> merge_one_into_another(ArrayList<Integer> first, ArrayList<Integer> second) {
        var f = first.size() - 1;
        var s = f; // the 2 subarrays are the same
        var z = second.size() - 1;
        while (z >= 0 && f >= 0) {
            if (s >= 0 && second.get(s) >= first.get(f)) {
                second.set(z, second.get(s));
                s--;
            } else {
                second.set(z, first.get(f));
                f--;
            }
            z--;
        }
        return second;
    }

    @ParameterizedTest
    @MethodSource("provideArrays")
    void test_merge_one_into_another(ArrayList<Integer> first, ArrayList<Integer> second, ArrayList<Integer> expected) {
        var actual = merge_one_into_another(first, second);
        assertThat(actual).isEqualTo(expected);
    }

}

