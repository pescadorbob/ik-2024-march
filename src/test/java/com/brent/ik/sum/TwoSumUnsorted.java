package com.brent.ik.sum;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
        var nums = new ArrayList(Arrays.asList(2, 7, 11, 15));
        var target = 9;
        var expected = Arrays.asList(0,1);
        var actual = twoSum(nums,target);
        assertThat(actual).isEqualTo(expected);
    }
    private ArrayList<Integer> twoSum(ArrayList<Integer> nums, int target){
        var hashed = new HashMap<Integer,Integer>();
        for (int i=0;i<nums.size();i++){
            hashed.put(nums.get(i),i);
        }
        for(int num:nums){
            var complement = target - num;
            if(hashed.containsKey(complement)){
                return new ArrayList<Integer>(Arrays.asList(hashed.get(num),hashed.get(complement)));
            }
        }
        return null;
    }
}
