package com.brent.ik.sum;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.sort;
import static org.assertj.core.api.Assertions.assertThat;

public class TwoSumCombinationsPreSortedTest {

    public static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(asList(2,2,2,3,3,3),5,9),
                Arguments.of(asList(2,3,2,3,2,3),5,9),
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
        sort(nums);
        int i=0;
        int j= nums.size()-1;
        var count = 0;
        while(i<j){
            if(nums.get(i) + nums.get(j) == target){
                var iCount = lookahead(nums,i,nums.get(i));
                var jCount = lookBehind(nums,j,nums.get(j));
                if((int) nums.get(i) == nums.get(j)){
                    count += nChooseTwo(i,j);
                    break;  // i and j crossed
                } else {
                    count += iCount * jCount;
                }
                i+=iCount;
                j-=jCount;
            } else if(nums.get(i) + nums.get(j) > target){
                j--; // go left to get lower number
            } else {
                i++; // go right to get higher number
            }
        }
        return count;
    }

    private int nChooseTwo(int i, int j) {
        return (j-i+1)*(j-i)/2;
    }

    private int lookBehind(List<Integer> nums, int j, Integer val) {
        var jCount = 0;
        while(nums.get(j)==val && j>0){
            j--;
            jCount++;

        }

        return jCount;
    }

    private int lookahead(List<Integer> nums, int i, Integer val) {
        var iCount = 0;
        while((int)nums.get(i) == val && i<nums.size()){
            i++;
            iCount++;
        }
        return iCount;
    }
}
