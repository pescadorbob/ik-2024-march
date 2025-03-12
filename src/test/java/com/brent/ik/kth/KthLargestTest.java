package com.brent.ik.kth;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static java.util.Collections.swap;
import static org.assertj.core.api.Assertions.assertThat;

public class KthLargestTest {
    private static Stream<Arguments> provideArrays() {
        return Stream.of(
                Arguments.of(list(1, 3, 5), 2, 3),
                Arguments.of(list(10, 10, 10), 2, 10),
                Arguments.of(list(9, 33, 4, 22, 11, 44, 55, 66, 1, 2, 3), 2, 55)
        );
    }

    private static ArrayList<Integer> list(Integer... array) {
        return new ArrayList<>(java.util.Arrays.stream(array).toList());
    }

    @ParameterizedTest
    @MethodSource("provideArrays")
    void test_kth_largest(ArrayList<Integer> array, Integer k, Integer expected) {
        var actual = kthLargest(array, k);
        assertThat(actual).isEqualTo(expected);
    }

    public static Integer kthLargest(List<Integer> nums, Integer k) {
        helper(nums, 0, nums.size() - 1, nums.size() - k);
        return nums.get(nums.size() - k);
    }

    private static void helper(List<Integer> nums, int start, int end, int index) {
        // base case
        if (end == start) return;
        var pivot = partition(nums, start, end);
        if (pivot == index) { // lucky case!
            return;
        } else {
            if (index < pivot) {
                helper(nums, start, pivot - 1, index);
            } else {
                helper(nums, pivot + 1, end, index);
            }
        }
    }

    private static Integer partition(List<Integer> nums, int start, int end) {
        var pivot = new Random(System.currentTimeMillis()).nextInt(start, end);
        swap(nums, start, pivot);
        var smaller = start;
        var larger = start + 1;
        while (larger <= end) {
            if (nums.get(larger) < nums.get(start)) {
                smaller++;
                swap(nums, smaller, larger);
            }
            larger++;
        }
        swap(nums, start, smaller);
        return smaller;
    }


}
