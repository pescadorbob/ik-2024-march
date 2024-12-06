package com.brent.ik.kth;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static java.lang.Math.pow;
import static java.util.Arrays.asList;
import static java.util.Collections.swap;
import static org.assertj.core.api.Assertions.assertThat;

public class KClosestPointsToOriginTest {
    private static Stream<Arguments> provideArrays() {
        return Stream.of(
                Arguments.of(asList(asList(1, 3), asList(-2, 2)), 1, asList(asList(-2, 2))),
                Arguments.of(asList(asList(1, 3), asList(-2, 2), asList(10, 10), asList(1, 1)), 2, asList(asList(1, 1), asList(-2, 2))),
                Arguments.of(asList(asList(3, 3), asList(5, -1), asList(-2, 4)), 2, asList(asList(3, 3), asList(-2, 4)))
        );
    }


    @ParameterizedTest
    @MethodSource("provideArrays")
    void testKClosestPointsToOrigin(List<List<Integer>> points, Integer k, List<List<Integer>> expected) {
        var actual = kClosest(points, k);
        assertThat(actual).containsOnlyElementsOf(expected);
    }

    private List<List<Integer>> kClosest(List<List<Integer>> points, Integer k) {
        helper(points, 0, points.size() - 1, k);
        var results = new ArrayList<List<Integer>>();
        for (int i = 0; i < k; i++) {
            results.add(asList(points.get(i).get(0), points.get(i).get(1)));
        }
        return results;
    }

    private static void helper(List<List<Integer>> points, int start, int end, int k) {
        if (start == end) return;
        int pivot = partition(points, start, end);
        if (pivot == k) {
            // lucky case!
            return;
        }
        if (k < pivot) {
            helper(points, start, pivot - 1, k);
        } else {
            helper(points, pivot + 1, end, k);
        }
    }

    private static int partition(List<List<Integer>> points, int start, int end) {
        int pIndex = new Random(System.currentTimeMillis()).nextInt(start, end);
        int smaller = start;
        swap(points, start, pIndex);

        for (int larger = smaller + 1; larger <= end; larger++) {
            if (compare(points, larger, pIndex)) {
                smaller++;
                swap(points, smaller, larger);
            }
        }
        swap(points, start, smaller);
        return smaller;
    }
    private static boolean compare(List<List<Integer>> points, int larger, int pivot){
        return (squared(points.get(larger)) < squared(points.get(pivot)));
    }

    private static Integer squared(List<Integer> c) {
        return c.get(0) * c.get(0) + c.get(1) * c.get(1);
    }
}
