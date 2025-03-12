package com.brent.ik.sum;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.sort;
import static org.assertj.core.api.Assertions.assertThat;

public class FourSumTest {


    private static Stream<Arguments> shouldFindUniqueQuadruplesThatSumUpToGivenValue() {
        return Stream.of(
                Arguments.of(emptyList(),0,emptyList()),
                Arguments.of(asList(1,2,3),0,emptyList()),
                Arguments.of(asList(1,2,3,4,5),0,emptyList()),
                Arguments.of(asList(0, 0, 1, 1, 1, 1, 1, 1, 1, 3, 2, -1, -6, 7), 3,
                        asList(asList(0, 0, 1, 2),
                                asList(0, 1, 1, 1),
                                asList(-1, 0, 1, 3),
                                asList(-6, 0, 2, 7),
                                asList(-1, 1, 1, 2),
                                asList(-6, 1, 1, 7),
                                asList(-6, -1, 3, 7)
                        )),
                Arguments.of(asList(0, 0, 1, 1, 1, 1, 1, 1, 1, 3, 2, -1), 3,
                        asList(asList(0, 0, 1, 2),
                                asList(0, 1, 1, 1),
                                asList(-1, 0, 1, 3),
                                asList(-1, 1, 1, 2))),
                Arguments.of(asList(-3, 0, 0, 1, 3, 2, -1), 3,
                        asList(
                                asList(0, 0, 1, 2),
                                asList(-1, 0, 1, 3),
                                asList(-3, 1, 2, 3))),
                Arguments.of(asList(-3, 0, 0, 1, 6, 2, -1), 3,
                        asList(
                                asList(-3, -1, 1, 6),
                                asList(-3, 0, 0, 6),
                                asList(0, 0, 1, 2)
                        ))

        );
    }

    @ParameterizedTest
    @MethodSource
    void shouldFindUniqueQuadruplesThatSumUpToGivenValue(List<Integer> arr, int target, List<List<Integer>> expected) {

        var actual = fourSum(arr, target);
        actual.forEach(list -> list.sort(Integer::compareTo));
        expected.forEach(list -> list.sort(Integer::compareTo));
        assertThat(actual)
                .as("The lists aren't equivalent")
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    private List<List<Integer>> fourSum(List<Integer> arr, int target) {

        List<List<Integer>> solution = new ArrayList<>();

        sort(arr);

        // 4sum loop
        for (int i = 0; i < arr.size() - 3; ) {
            var fourSumValue = arr.get(i);
            for (int j = i + 1; j < arr.size() - 2; ) {
                var threeSumValue = arr.get(j);
                var twoSumTarget = target - fourSumValue - threeSumValue;
                var left = j + 1;
                var right = arr.size() - 1;
                while (left < right) {
                    var sum = arr.get(left) + arr.get(right);
                    if (sum == twoSumTarget) {
                        var sol = asList(fourSumValue, threeSumValue, arr.get(left), arr.get(right));
                        solution.add(sol);
                        left = advanceLeftUntilDifferent(arr, left, right);

                    } else if (sum > twoSumTarget) {
                        right = decreaseRightUntilDifferent(arr, left, right);
                    } else {
                        left = advanceLeftUntilDifferent(arr, left, right);
                    }
                }
                j = advanceLeftUntilDifferent(arr, j, arr.size() - 1);
            }
            i = advanceLeftUntilDifferent(arr, i, arr.size() - 1);
        }
        return solution;
    }

    private int decreaseRightUntilDifferent(List<Integer> arr, int left, int right) {
        var currVal = arr.get(right);
        while ((int) currVal == arr.get(right) && left < right) {
            right--;
        }
        return right;
    }

    private int advanceLeftUntilDifferent(List<Integer> arr, int left, int right) {
        var currVal = arr.get(left);

        while ((int) currVal == arr.get(left) && left < right) {
            left++;
        }
        return left;
    }

}
/*
 target = 3
 solution: -3 0 0 3, -3 1 2 3, -1 0 1 3, duplicate... every level has to advance over duplicate numbers.
    i     j  l     r
 0  1  2  3  4  5  6
-3 -1  0  0  1  2  3
 */

