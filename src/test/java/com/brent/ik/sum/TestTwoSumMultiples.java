package com.brent.ik.sum;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class TestTwoSumMultiples {
    public static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of(asList(2, 7, 11, 15), 9, asList(asList(2, 7))),
                Arguments.of(asList(2, 7, 5, 4, 11, 15), 9, asList(asList( 4,5), asList(2, 7))),
                Arguments.of(asList(2, 2, 2, 2, 2, 2, 2, 7, 7, 7, 7, 7, 7, 7, 11, 15), 9, asList(asList(2, 7))),
                Arguments.of(asList(2, 2, 2, 2, 2, 2, 2, 7, 7, 7, 7, 7, 7, 7, 5, 4, 11, 15), 9, asList(asList(2, 7), asList( 4,5)))
        );
    }

    /*
    Given an array of numbers, find all the unique pairs that add up to a sum.
    For easy comparison, the pairs should always be sorted in increasing order.
         */
    @ParameterizedTest
    @MethodSource("params")
    void shouldProduceIndices_1_2_given_2_7_11_15_andTarget_9(List<Integer> nums, int target, List<List<Integer>> expected) {

        var actual = twoSum(nums, target);
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().containsOnlyElementsOf(expected);
    }

    private List<List<Integer>> twoSum(List<Integer> nums, int target) {
        Collections.sort(nums);
        // since it's from both sides, we can use a double pointer approach to find it. We start on either end and move
        // from the left or right depending on if the actual sum is greater or less than the target.
        int i = 0;
        int j = nums.size() - 1;
        var result = new ArrayList<List<Integer>>();
        while (i < j) {
            int sum = nums.get(i) + nums.get(j);
            if (sum == target) {
                var pair = asList(nums.get(i), nums.get(j));
                result.add(pair);
                i = incrementToNextDistinctValue(i, nums);
                j = decrementToNextDistinctValue(j, nums);

            } else if (sum < target) {
                // increase from the left to make the sum greater
                i = incrementToNextDistinctValue(i, nums);

            } else {
                j = decrementToNextDistinctValue(j, nums);
            }
        }
        return result;
    }

    private int decrementToNextDistinctValue(int j, List<Integer> nums) {
        var start = nums.get(j);
        var index = j;
        while (Objects.equals(nums.get(index), start) && index > 0) {
            index--;
        }
        return index;
    }

    private int incrementToNextDistinctValue(int i, List<Integer> nums) {
        var start = nums.get(i);
        var index = i;
        while (Objects.equals(nums.get(index), start) && index < nums.size()) {
            index++;
        }
        return index;
    }
}
