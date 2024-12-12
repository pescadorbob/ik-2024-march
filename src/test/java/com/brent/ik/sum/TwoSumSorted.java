package com.brent.ik.sum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class TwoSumSorted {
    public static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of(asList(2,7,11,15),9,asList(1,2))
        );
    }

    /*
    167. Two Sum Il - Input array is sorted
    Easy 6 1 207 Q 487 0 Favorite CJ Share
    Given an array of integers that is already sorted in ascending order, find two numbers such that they
    add up to a specific target number.
    The function twoSum should return indices of the two numbers such that they add up to the target,
    where indexl must be less than index2.
    Note:
    • Your returned answers (both indexl and index2) are not zero-based.
    • You may assume that each input would have exactly one solution and you may not use the same
    element twice.
    Example:
    Input: numbers — [2,7,11,15], target = 9
    Output: [1, 2]
    Explanation: The sum of 2 and 7 is 9, Therefore index1 = 1, index2 = 2
         */
    @ParameterizedTest
    @MethodSource("params")
    void shouldProduceIndices_1_2_given_2_7_11_15_andTarget_9(List<Integer> nums,int target, List<Integer> expected) {

        var actual = twoSum(nums,target);
        assertThat(actual).isEqualTo(expected);
    }
    private List<Integer> twoSum(List<Integer> nums, int target){
        // since it's from both sides, we can use a double pointer approach to find it. We start on either end and move
        // from the left or right depending on if the actual sum is greater or less than the target.
        int i = 0;
        int j = nums.size()-1;
        while(i<j){
            int sum = nums.get(i) + nums.get(j);
            if(sum == target){
                return new ArrayList<>(asList(i+1,j+1));
            } else if (sum < target){
                // increase from the left to make the sum greater
                i++;
            } else {
                j--;
            }
        }
        return Collections.emptyList();
    }
}
