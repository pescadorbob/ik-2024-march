package com.brent.ik.sum;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class FourSumTest {
    private static ArrayList<ArrayList<Integer>> lists(ArrayList<Integer>... array) {
        return new ArrayList<ArrayList<Integer>>(Arrays.stream(array).toList());
    }

    private static ArrayList<Integer> list(Integer... array) {
        return new ArrayList<>(java.util.Arrays.stream(array).toList());
    }

    private static Stream<Arguments> shouldFindUniqueQuadruplesThatSumUpToGivenValue() {
        return Stream.of(
                Arguments.of(list(0, 0, 1, 3, 2, -1), 3, lists(list(-1, 0, 1, 3), list(0, 0, 1, 2))),
                Arguments.of(list(-3, 0, 0, 1, 3, 2, -1), 3,
                        lists(
                                list(-1, 0, 1, 3),
                                list(-3, 1, 2, 3),
                                list(0, 0, 1, 2))),
                Arguments.of(list(-3, 0, 0, 1, 6, 2, -1), 3,
                        lists(list(0, 0, 1, 2),
                                list(-3, 0, 0, 6),
                                list(-3, -1, 1, 6)))

        );
    }

    @ParameterizedTest
    @MethodSource
    void shouldFindUniqueQuadruplesThatSumUpToGivenValue(ArrayList arr, int target, ArrayList<ArrayList<Integer>> expected) {

        var actual = fourSum(arr, target);
        assertThat(actual).containsOnlyElementsOf(expected);
    }

    private ArrayList<ArrayList<Integer>> fourSum(ArrayList<Integer> arr, int target) {
        sort(arr);
        // -1,0,0,1,2
        // try the first one [-1], then find 3 numbers that add to target minus first one
        var leftIndex = 0;
        var rightIndex = arr.size() - 1;
        var results = new HashSet<ArrayList<Integer>>();
        while (leftIndex < rightIndex - 1) {
            var firstOne = arr.get(leftIndex);
            var threeSumTarget = target - firstOne;
            var otherThree = threeSum(arr, leftIndex + 1, rightIndex, threeSumTarget);
            addThreeSumResultToFirstOne(firstOne, otherThree, results);
            leftIndex++;
        }
        return new ArrayList<>(results);

    }

    private void addThreeSumResultToFirstOne(Integer firstOne, ArrayList<ArrayList<Integer>> otherThree, Set<ArrayList<Integer>> results) {
        otherThree.forEach(it -> {
            var result = new ArrayList<Integer>();
            result.add(firstOne);
            result.addAll(it);
            results.add(result);
        });

    }

    private static void sort(ArrayList<Integer> arr) {
        Collections.sort(arr);
    }

    private ArrayList<ArrayList<Integer>> threeSum(ArrayList<Integer> arr, int left, int right, int threeSumTarget) {
        var results = new ArrayList<ArrayList<Integer>>();
        while (left < right - 1) {
            var firstOne = arr.get(left);
            var twoSumTarget = threeSumTarget - firstOne;
            twoSum(arr, left + 1, right, twoSumTarget, results, firstOne);
            left++;
        }
        return results;
    }

    private static void twoSum(ArrayList<Integer> arr, int left, int right, int newTarget, ArrayList<ArrayList<Integer>> results, Integer firstOne) {
        while (left < right) {

            var sum = arr.get(left) + arr.get(right);
            if (sum == newTarget) {
                results.add(new ArrayList(Arrays.asList(firstOne, arr.get(left), arr.get(right))));
                left++;
            } else if (sum > newTarget) {
                right--;
            } else {
                left++;
            }
        }
    }
}
