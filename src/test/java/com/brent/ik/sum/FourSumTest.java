package com.brent.ik.sum;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class FourSumTest {


    private static Stream<Arguments> shouldFindUniqueQuadruplesThatSumUpToGivenValue() {
        return Stream.of(
                Arguments.of(asList(0, 0, 1, 3, 2, -1), 3, asList(asList(-1, 0, 1, 3), asList(0, 0, 1, 2))),
                Arguments.of(asList(-3, 0, 0, 1, 3, 2, -1), 3,
                        asList(
                                asList(-1, 0, 1, 3),
                                asList(-3, 1, 2, 3),
                                asList(0, 0, 1, 2))),
                Arguments.of(asList(-3, 0, 0, 1, 6, 2, -1), 3,
                        asList(asList(0, 0, 1, 2),
                                asList(-3, 0, 0, 6),
                                asList(-3, -1, 1, 6)))

        );
    }

    @ParameterizedTest
    @MethodSource
    void shouldFindUniqueQuadruplesThatSumUpToGivenValue(List arr, int target, List<List<Integer>> expected) {

        var actual = fourSum(arr, target);
        assertThat(actual).containsOnlyElementsOf(expected);
    }

    private List<List<Integer>> fourSum(List<Integer> arr, int target) {
        sort(arr);
        // -1,0,0,1,2
        // try the first one [-1], then find 3 numbers that add to target minus first one
        var leftIndex = 0;
        var rightIndex = arr.size() - 1;
        var results = new HashSet<List<Integer>>();
        while (leftIndex < rightIndex - 1) {
            var firstOne = arr.get(leftIndex);
            var threeSumTarget = target - firstOne;
            var otherThree = threeSum(arr, leftIndex + 1, rightIndex, threeSumTarget);
            addThreeSumResultToFirstOne(firstOne, otherThree, results);
            leftIndex++;
        }
        return new ArrayList<>(results);

    }

    private void addThreeSumResultToFirstOne(Integer firstOne, List<List<Integer>> otherThree, Set<List<Integer>> results) {
        otherThree.forEach(it -> {
            var result = new ArrayList<Integer>();
            result.add(firstOne);
            result.addAll(it);
            results.add(result);
        });

    }

    private static void sort(List<Integer> arr) {
        Collections.sort(arr);
    }

    private List<List<Integer>> threeSum(List<Integer> arr, int left, int right, int threeSumTarget) {
        var results = new ArrayList<List<Integer>>();
        while (left < right - 1) {
            var firstOne = arr.get(left);
            var twoSumTarget = threeSumTarget - firstOne;
            twoSum(arr, left + 1, right, twoSumTarget, results, firstOne);
            left++;
        }
        return results;
    }

    private static void twoSum(List<Integer> arr, int left, int right, int newTarget, List<List<Integer>> results, Integer firstOne) {
        while (left < right) {

            var sum = arr.get(left) + arr.get(right);
            if (sum == newTarget) {
                results.add(new ArrayList(asList(firstOne, arr.get(left), arr.get(right))));
                left++;
            } else if (sum > newTarget) {
                right--;
            } else {
                left++;
            }
        }
    }
}
