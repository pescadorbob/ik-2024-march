package com.brent.ik.sum;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class FourSumTest {
    @Test
    void shouldFindUniqueQuadruplesThatSumUpToGivenValue(){
        var arr = new ArrayList(Arrays.asList(0,0,1,3,2,-1));
        var target = 3;
        var expected = Arrays.asList(Arrays.asList(-1,0,1,3),Arrays.asList(0,0,1,2));
        var actual = fourSum(arr,target);
        assertThat(actual).containsOnlyElementsOf(expected);
    }

    private ArrayList<ArrayList<Integer>> fourSum(ArrayList<Integer> arr, int target) {
        sort(arr);
        // -1,0,0,1,2
        // try the first one [-1], then find 3 numbers that add to target minus first one
        var leftIndex = 0;
        var rightIndex = arr.size()-1;
        var results = new HashSet<ArrayList<Integer>>();
        while(leftIndex<rightIndex-1){
            var firstOne = arr.get(leftIndex);
            var threeSumTarget = target - firstOne;
            var otherThree = threeSum(arr,leftIndex+1,rightIndex,threeSumTarget);
            addThreeSumResultToFirstOne(firstOne,otherThree,results);
            leftIndex ++;
        }
        return new ArrayList<>(results);

    }

    private void addThreeSumResultToFirstOne(Integer firstOne, ArrayList<ArrayList<Integer>> otherThree, Set<ArrayList<Integer>> results) {
        otherThree.forEach(it->{
            var result = new ArrayList<Integer>();
            result.add(firstOne);
            result.addAll(it);
            results.add(result);
        });

    }

    private static void sort(ArrayList<Integer> arr) {
        Collections.sort(arr);
    }

    private ArrayList<ArrayList<Integer>> threeSum(ArrayList<Integer> arr, int left, int right, int target) {
        var firstOne = arr.get(left);
        left ++;
        var newTarget = target-firstOne;
        var results = new ArrayList<ArrayList<Integer>>();
        while(left<right){
            var sum = arr.get(left) + arr.get(right);
            if(sum == newTarget){
                results.add(new ArrayList(Arrays.asList(firstOne,arr.get(left),arr.get(right))));
                left++;
            } else if(sum>newTarget){
                right--;
            } else {
                left++;
            }
        }
        return results;
    }
}
