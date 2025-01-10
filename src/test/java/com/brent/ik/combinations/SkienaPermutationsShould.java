package com.brent.ik.combinations;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class SkienaPermutationsShould {
    @Test
    void produceAllPermutations_givenAnArray(){

        int[] array = {1, 2, 3};
        List<List<Integer>> expected = asList(asList(1,2,3),asList(1,3,2),asList(3,2,1),asList(3,1,2),asList(2,1,3),asList(2,3,1));

        List<List<Integer>> result = generatePermutations(array);

        assertThat(result).containsOnlyOnceElementsOf(expected);
    }

    public List<List<Integer>> generatePermutations(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int[] partialSolution = new int[nums.length];
        boolean[] used = new boolean[nums.length];
        backtrack(result, nums, partialSolution, used, 0);
        return result;
    }

    private void backtrack(List<List<Integer>> result, int[] nums, int[] solution, boolean[] used, int k) {
        if (is_a_solution(solution,k,nums)) {
            process_solution(result, nums, solution);
        } else {
            int[] candidates = construct_candidates(nums, solution, used, k);
            for (int i = 0; i < candidates.length; i++) {
                solution[k] = candidates[i];
                make_move(nums, solution, used, k, candidates[i]);
                backtrack(result, nums, solution, used, k + 1);
                unmake_move(nums, solution, used, k, candidates[i]);
            }
        }
    }

    private boolean is_a_solution(int[] partialSolution,int k, int[] input) {
        return k == input.length;
    }

    private int[] construct_candidates(int[] nums, int[] solution, boolean[] used, int k) {
        List<Integer> candidates = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (!used[i]) {
                candidates.add(nums[i]);
            }
        }
        int[] result = new int[candidates.size()];
        for (int i = 0; i < candidates.size(); i++) {
            result[i] = candidates.get(i);
        }
        return result;
    }

    private void process_solution(List<List<Integer>> result, int[] nums, int[] solution) {
        List<Integer> permutation = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            permutation.add(solution[i]);
        }
        result.add(permutation);
    }

    private void make_move(int[] nums, int[] solution, boolean[] used, int k, int candidate) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == candidate) {
                used[i] = true;
                break;
            }
        }
    }

    private void unmake_move(int[] nums, int[] solution, boolean[] used, int k, int candidate) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == candidate) {
                used[i] = false;
                break;
            }
        }
    }
}
/*
```

In this implementation:

        1. `is_a_solution`: Checks if we have considered all elements in the array.
        2. `construct_candidates`: Generates candidates for the next position (unused elements).
        3. `process_solution`: Processes a complete solution (a permutation) and adds it to the result list.
        4. `make_move`: Marks an element as used.
5. `unmake_move`: Marks an element as unused.

This structure follows Skiena's encapsulation approach for generating permutations. Let me know if you need further adjustments or have any questions! Happy coding! 😊
*/