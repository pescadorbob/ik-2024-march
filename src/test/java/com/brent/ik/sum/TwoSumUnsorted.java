package com.brent.ik.sum;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class TwoSumUnsorted {
    /*
Given an array of integers, return indices of the two numbers such that they add up to a specific target.
You may assume that each input would have exactly one solution, and you may not use the same element twice.
Example:

Given nums = [2,7,11,15], target = 9
Because nums [0] + nums[1] = 2 + 7 = 9
return [0, 1].
     */
    @Test
    void shouldProduceIndices_0_1_given_2_7_11_15_andTarget_9() {
        var nums = new ArrayList(asList(2, 7, 11, 15));
        var target = 9;
        var expected = asList(0,1);
        var actual = twoSum(nums,target);
        assertThat(actual).isEqualTo(expected);
    }
    private List<Integer> twoSum(ArrayList<Integer> nums, int target){
        var hset = new HashMap<Integer,Integer>(); // value -> index
        for(int m=0;m<nums.size();m++){
            if(hset.containsKey( target-nums.get(m) )){
                return asList(hset.get(target-nums.get(m)),m);
            } else {
                hset.put(nums.get(m),m);
            }
        }
        return null;
    }
}
