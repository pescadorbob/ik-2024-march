package com.brent.ik.sum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class TwoSumCombinationsTest {

    public static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(asList(2,2,2,3,3,3),5,9),
                Arguments.of(asList(-1,4,5,10,-1,-10,10),9,5)
        );
    }

    @ParameterizedTest
    @MethodSource("input")
    void shouldCountPairsOfNumbersAddToGivenTargetIncludingAllCombinations(List<Integer> nums, int target, int expected){
        var actual = twoSumCountWithCombinations(nums,target);
        assertThat(actual).isEqualTo(expected);
    }

    private int twoSumCountWithCombinations(List<Integer> nums, int target) {

        var count = 0;
        var frequencyMap = new HashMap<Integer,Integer>();
        for(int m=0;m<nums.size();m++){
            var mTarget = target - nums.get(m);
            if(frequencyMap.containsKey(mTarget)){
                count += frequencyMap.get(mTarget);
            }

            frequencyMap.put(nums.get(m),frequencyMap.getOrDefault(nums.get(m),0) + 1);
        }
        return count;
    }
}
