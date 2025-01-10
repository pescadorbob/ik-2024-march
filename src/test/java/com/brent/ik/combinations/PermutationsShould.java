package com.brent.ik.combinations;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class PermutationsShould {
    @Test
    void produceEveryPermutation_givenAnInputArray(){
        int[] array = {1, 2, 3};
        List<List<Integer>> expected = asList(asList(1,2,3),asList(1,3,2),asList(3,2,1),asList(3,1,2),asList(2,1,3),asList(2,3,1));
        List<List<Integer>> result = permute(array);
        assertThat(result).containsOnlyOnceElementsOf(expected);
    }

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), nums);
        return result;
    }

    private static void backtrack(List<List<Integer>> result, List<Integer> tempList, int[] nums) {
        if (tempList.size() == nums.length) {
            result.add(new ArrayList<>(tempList));
        } else {
            for (int i = 0; i < nums.length; i++) {
                if (tempList.contains(nums[i])) continue;
                tempList.add(nums[i]);
                backtrack(result, tempList, nums);
                tempList.remove(tempList.size() - 1);
            }
        }
    }
}
/*
```

In this code:

1. The `permute` method initializes the result list and calls the `backtrack` method.
2. The `backtrack` method constructs permutations by adding elements to `tempList`.
3. If the `tempList` size equals the array length, a new permutation is added to the result list.
4. The method uses recursion and backtracking to generate all possible permutations.
 */