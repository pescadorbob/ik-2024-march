package com.brent.ik.combinations;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class SubsetsShould {
    @Test
    void printAllSubsets_givenAnArray(){
        int[] array = {1, 2, 3};
        List<List<Integer>> expected = asList(asList(),asList(1),asList(1,2),asList(1,2,3),asList(1,3),asList(2),asList(2,3),asList(3));
        List<List<Integer>> result = subsets(array);

        assertThat(result).containsOnlyOnceElementsOf(expected);

    }

    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), nums, 0);
        return result;
    }

    private static void backtrack(List<List<Integer>> result, List<Integer> tempList, int[] nums, int start) {
        result.add(new ArrayList<>(tempList));
        for (int i = start; i < nums.length; i++) {
            tempList.add(nums[i]);
            backtrack(result, tempList, nums, i + 1);
            tempList.remove(tempList.size() - 1);
        }
    }
}
/*
Generating all subsets (also known as the power set) of an array can be done using a backtracking approach. Here's an example of how you can implement it in Java:

In this code:

1. The `subsets` method initializes the result list and calls the `backtrack` method.
2. The `backtrack` method constructs subsets by adding elements to `tempList`.
3. Each subset is added to the result list whenever `backtrack` is called.
4. The method uses recursion and backtracking to generate all possible subsets.
 */