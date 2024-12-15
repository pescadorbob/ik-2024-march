package com.brent.ik.sum;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.sort;
import static org.assertj.core.api.Assertions.assertThat;

public class ThreeSumSmallerCombinationsTest {

    public static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(asList(-2,0,1,3), 2, 2)
        );
    }

    @ParameterizedTest
    @MethodSource("input")
    void shouldCountPairsOfNumbersAddToGivenTargetIncludingAllCombinations(List<Integer> nums, int target, int expected) {
        var actual = threeSumSmallerCombinations(nums, target);
        assertThat(actual).isEqualTo(expected);
    }

    private int threeSumSmallerCombinations(List<Integer> nums, int target) {
        sort(nums);
        var count = 0;
        for (int m = 0; m < nums.size(); m++) {
            count += twoSumCountWithCombinations(nums,m - 1,  target - nums.get(m));
        }
        return count;
    }

    private int twoSumCountWithCombinations(List<Integer> nums,int m, int target) {
        int i = 0;
        int j = m;
        var count = 0;
        while (i < j) {
            if(nums.get(i) + nums.get(j) >= target ){
                j--;
            } else {
                count += j-i;
                i++;
            }
        }
        return count;
    }


}
