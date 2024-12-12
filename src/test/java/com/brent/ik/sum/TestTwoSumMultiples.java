package com.brent.ik.sum;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class TestTwoSumMultiples {
    public static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of(asList(2,7,11,15),9,asList(2,7))
        );
    }

    /*
    Given an array of numbers find all the unique pairs that add up to a sum.
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
                return asList(nums.get(i),nums.get(j));
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
