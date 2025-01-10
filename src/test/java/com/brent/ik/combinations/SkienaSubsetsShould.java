package com.brent.ik.combinations;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class SkienaSubsetsShould {
    @Test
    void makeEverySubset_givenAnArray(){

        int[] array = {1, 2, 3};
        SkienaSubsetsShould ss = new SkienaSubsetsShould();
        List<List<Integer>> result = ss.generateSubsets(array);
        List<List<Integer>> expected = asList(asList(),asList(1),asList(1,2),asList(1,2,3),asList(1,3),asList(2),asList(2,3),asList(3));
        assertThat(result).containsOnlyOnceElementsOf(expected);
    }

    public List<List<Integer>> generateSubsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int[] solution = new int[nums.length];
        backtrack(result, nums, solution, 0);
        return result;
    }

    private void backtrack(List<List<Integer>> result, int[] nums, int[] solution, int k) {
        if (is_a_solution(nums, k)) {
            process_solution(result, nums, solution);
        } else {
            int[] candidates = construct_candidates(nums, solution, k);
            for (int i = 0; i < candidates.length; i++) {
                solution[k] = candidates[i];
                make_move(nums, solution, k);
                backtrack(result, nums, solution, k + 1);
                unmake_move(nums, solution, k);
            }
        }
    }

    private boolean is_a_solution(int[] nums, int k) {
        return k == nums.length;
    }

    private int[] construct_candidates(int[] nums, int[] solution, int k) {
        return new int[]{0, 1}; // Include or exclude
    }

    private void process_solution(List<List<Integer>> result, int[] nums, int[] solution) {
        List<Integer> subset = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (solution[i] == 1) {
                subset.add(nums[i]);
            }
        }
        result.add(subset);
    }

    private void make_move(int[] nums, int[] solution, int k) {
        // No specific move to make
    }

    private void unmake_move(int[] nums, int[] solution, int k) {
        // No specific move to unmake
    }
}
