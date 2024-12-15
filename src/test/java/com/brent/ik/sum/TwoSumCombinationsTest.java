package com.brent.ik.sum;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class TwoSumCombinationsTest {
    @Test
    void shouldCountPairsOfNumbersAddToGivenTargetIncludingAllCombinations(){
        var nums = asList(2,2,2,3,3,3);
        var target = 5;
        var expected = 9;
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
