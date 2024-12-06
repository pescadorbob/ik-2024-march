package com.brent.ik.kth;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static com.brent.ik.kth.KthLargestTest.kthLargest;
import static java.lang.Math.abs;
import static java.util.Arrays.asList;
import static java.util.Collections.swap;
import static org.assertj.core.api.Assertions.assertThat;

public class KElementsClosestToTheMedianTest {
    private static Stream<Arguments> provideArrays() {
        return Stream.of(
                Arguments.of(asList(1, 3, -10, 4), 2, asList(1, 3)),
                Arguments.of(asList(1, 3, -10, 4,-33,12,55,-25), 2, asList(1, 3))
        );
    }


    @ParameterizedTest
    @MethodSource("provideArrays")
    void testKClosestToTheMedian(List<Integer> points, Integer k, List<Integer> expected) {
        var median = calculateMedian(points);
        var actual = kClosest(points, k, median);
        assertThat(actual).containsOnlyElementsOf(expected);
    }

    private Integer calculateMedian(List<Integer> points) {
        if (points.size() % 2 == 1) return kthLargest(points, points.size() / 2);
        var bottom = kthLargest(points, (points.size() / 2) + 1);
        var top = kthLargest(points, points.size() / 2);
        return (top + bottom) / 2;
    }

    private List<Integer> kClosest(List<Integer> points, Integer k, int median) {
        helper(points, 0, points.size() - 1, k, median);
        var results = new ArrayList<Integer>();
        for (int i = 0; i < k; i++) {
            results.add(points.get(i));
        }
        return results;
    }

    private static void helper(List<Integer> points, int start, int end, int k, int median) {
        if (start == end) return;
        int pivot = partition(points, start, end, median);
        if (pivot == k) {
            // lucky case!
            return;
        }
        if (k < pivot) {
            helper(points, start, pivot - 1, k, median);
        } else {
            helper(points, pivot + 1, end, k, median);
        }
    }

    private static int partition(List<Integer> points, int start, int end, int median) {
        int pIndex = new Random(System.currentTimeMillis()).nextInt(start, end);
        int smaller = start;
        int pivot = points.get(pIndex);
        swap(points, start, pIndex);

        for (int larger = smaller + 1; larger <= end; larger++) {
            if (compare(points.get( larger), pivot, median)) {
                smaller++;
                swap(points, smaller, larger);
            }
        }
        swap(points, start, smaller);
        return smaller;
    }

    private static boolean compare(int larger, int pivot, int median) {
        return distance(larger, median) <= distance(pivot, median);
    }

    private static int distance(int p1, int p2) {
        return abs(p1 - p2);
    }


}
