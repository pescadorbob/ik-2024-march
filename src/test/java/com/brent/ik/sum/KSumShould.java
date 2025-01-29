package com.brent.ik.sum;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.sort;
import static org.assertj.core.api.Assertions.assertThat;

public class KSumShould {


    private static Stream<Arguments> find_unique_Quadruples_that_sum_up_to_given_value() {
        return Stream.of(
                Arguments.of(emptyList(), 0, emptyList()),
                Arguments.of(asList(1, 2, 3), 0, emptyList()),
                Arguments.of(asList(1, 2, 3, 4, 5), 0, emptyList()),
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
    void find_unique_Quadruples_that_sum_up_to_given_value(List<Integer> arr, int target, List<List<Integer>> expected) {

        var actual = fourSum(arr, target);
        actual.forEach(list -> list.sort(Integer::compareTo));
        expected.forEach(list -> list.sort(Integer::compareTo));
        assertThat(actual)
                .as("The lists aren't equivalent")
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    public static List<List<Integer>> fourSum(List<Integer> arr, int target) {

        List<List<Integer>> solution = new ArrayList<>();

        sort(arr);

        helper(arr, 4, 0, target, solution, new ArrayList<Integer>());

        // 4sum loop
        return solution;
    }

    private static void helper(List<Integer> arr, int k, int i, int target, List<List<Integer>> solution, Collection<Integer> partial) {
        if (arr.size() < k || arr.get(i) * k > target || arr.getLast() * k < target) return;
        if (k == 2) {
            twoSum(arr, i, target, solution, partial);
        } else {
            while (i < arr.size() - k + 1) {
                var nextPartial = new ArrayList<>(partial);
                nextPartial.add(arr.get(i));
                helper(arr, k - 1, i + 1, target - arr.get(i), solution, nextPartial);
                i = advanceLeftUntilDifferent(arr, i, arr.size());
            }
        }

    }

    private static void twoSum(List<Integer> arr, int i, int target, List<List<Integer>> solution, Collection<Integer> partial) {
        // two pointer
        var left = i;
        var right = arr.size() - 1;
        while (left < right) {
            if (arr.get(left) + arr.get(right) == target) {
                // sum found!
                var sol = new ArrayList<>(asList(arr.get(left), arr.get(right)));
                sol.addAll(partial);
                solution.add(sol);
                left = advanceLeftUntilDifferent(arr, left, right);
                right = decreaseRightUntilDifferent(arr, left, right);
            } else if (arr.get(left) + arr.get(right) < target) {
                left = advanceLeftUntilDifferent(arr, left, right);
            } else {
                right = decreaseRightUntilDifferent(arr, left, right);
            }
        }
    }

    private static int decreaseRightUntilDifferent(List<Integer> arr, int left, int right) {
        var currVal = arr.get(right);
        while ((int) currVal == arr.get(right) && left < right) {
            right--;
        }
        return right;
    }

    private static int advanceLeftUntilDifferent(List<Integer> arr, int left, int right) {
        var currVal = arr.get(left);

        while ((int) currVal == arr.get(left) && left < right) {
            left++;
        }
        return left;
    }

}
/*
 target = 3
 solution: -
 k  t
 4  3   i
 3  0   i  j
 2  -1  i  j  l           r
 3  0   i     j
 2  0   i     j  l        r
 2  0   i           j  l     r
        0  1  2  3  4  5  6
       -3 -1  0  0  1  2  3
 */

