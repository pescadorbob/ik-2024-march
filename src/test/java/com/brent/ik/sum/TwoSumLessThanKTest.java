package com.brent.ik.sum;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Math.max;
import static java.util.Arrays.asList;
import static java.util.Collections.sort;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class TwoSumLessThanKTest {
    public static Stream<Arguments> nums() {
        return Stream.of(
                Arguments.of(asList(34,23,1,24),25,24),
                Arguments.of(asList(34,23,1,24,75,33,54,8),60,58),
                Arguments.of(asList(10,20,30),15,-1)
        );
    }

    /*
        Given an array nums of integers and integer k, return the maximum sum such that there exists i < j with
        nums[i] + nums[j] = sum and sum < k. If no i,j exist satisfyning this equation, return -1
         */
    @ParameterizedTest
    @MethodSource("nums")
    void shouldFindClosestTarget_givenCandidates(List<Integer> nums,int target, int expected) {
        var actual = twoSumLessThanK(nums,target);
        assertThat(actual).isEqualTo(expected);
    }

    private int twoSumLessThanK(List<Integer> nums, int target) {
        sort(nums);
        var maximum = -1;
        int l = 0;
        int r = nums.size()-1;
        while(l<r){
            var sum = nums.get(l) + nums.get(r);
            if(sum<target){
                maximum = replaceMaximumIfSumIsGreater(maximum, sum);
                l = eliminateRightMostColumn(l);
            } else {
                r = eliminateTopMostColumn(r);
            }
        }
        return maximum;
    }

    private static int eliminateTopMostColumn(int r) {
        r--;
        return r;
    }

    private static int eliminateRightMostColumn(int l) {
        l++;
        return l;
    }

    private static int replaceMaximumIfSumIsGreater(int maximum, int sum) {
        maximum = max(sum, maximum);
        return maximum;
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
