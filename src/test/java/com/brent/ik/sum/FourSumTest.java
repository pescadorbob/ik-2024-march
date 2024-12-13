package com.brent.ik.sum;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.sort;
import static org.assertj.core.api.Assertions.assertThat;

public class FourSumTest {


    private static Stream<Arguments> shouldFindUniqueQuadruplesThatSumUpToGivenValue() {
        return Stream.of(
                Arguments.of(asList(0, 0, 1, 1, 1, 1, 1, 1, 1, 3, 2, -1), 3,
                        asList(asList(-1, 0, 1, 3))),
                Arguments.of(asList(-3, 0, 0, 1, 3, 2, -1), 3,
                        asList(
                                asList(-1, 0, 1, 3),
                                asList(-3, 1, 2, 3))),
                Arguments.of(asList(-3, 0, 0, 1, 6, 2, -1), 3,
                        asList(
                                asList(-3, -1, 1, 6)))

        );
    }

    @ParameterizedTest
    @MethodSource
    void shouldFindUniqueQuadruplesThatSumUpToGivenValue(List<Integer> arr, int target, List<List<Integer>> expected) {

        var actual = fourSum(arr, target);
        assertThat(actual).containsOnlyElementsOf(expected);
    }

    private List<List<Integer>> fourSum(List<Integer> arr, int target) {
        sort(arr);
        // -1,0,0,1,3
        // try the first one [-1], then find 3 numbers that add to target minus first one
        var leftIndex = 0;
        var rightIndex = arr.size() - 1;
        var results = new ArrayList<List<Integer>>();
        while (leftIndex < rightIndex - 1) {
            var firstOne = arr.get(leftIndex);
            var threeSumTarget = target - firstOne;
            threeSum(arr, incrementLeftUntilUnique(leftIndex,arr), rightIndex, threeSumTarget, results, firstOne);
            leftIndex = incrementLeftUntilUnique(leftIndex, arr);
        }
        return new ArrayList<>(results);

    }


    private void threeSum(List<Integer> arr, int left, int right, int threeSumTarget, List<List<Integer>> results, Integer fourVal) {
        while (left < right - 1) {
            var firstOne = arr.get(left);
            var twoSumTarget = threeSumTarget - firstOne;
            twoSum(arr, incrementLeftUntilUnique(left,arr),  twoSumTarget, results, fourVal, firstOne);
            left = incrementLeftUntilUnique(left, arr);
        }
    }

    private static void twoSum(List<Integer> arr, int left, int newTarget,
                               List<List<Integer>> results,
                               Integer fourVal,
                               Integer threeVal) {
        int n = arr.size();
        int right = n-1;
        while (left < right) {

            var sum = arr.get(left) + arr.get(right);
            if (sum == newTarget) {

                results.add(asList(fourVal, threeVal, arr.get(left), arr.get(right)));
                left = incrementLeftUntilUnique(left, arr);
            } else if (sum > newTarget) {
                right--;
            } else {
                left++;
            }
        }
    }

    private static int incrementLeftUntilUnique(int left, List<Integer> arr) {
        var curr = arr.get(left);
        while (left < arr.size() && (int) arr.get(left) == curr) {
            left++;
        }
        return left;
    }
}
