package com.brent.ik.recursive;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;

class NQueensTest {

    @Test
    void nQueens() {
        int n = 4;
        ArrayList<List<String>> expectedSolution = new ArrayList<>();
        List<String> solution1 = new ArrayList<>();
        solution1.add("--q-");
        solution1.add("q---");
        solution1.add("---q");
        solution1.add("-q--");
        expectedSolution.add(solution1);
        List<String> solution2 = new ArrayList<>();
        solution2.add("-q--");
        solution2.add("---q");
        solution2.add("q---");
        solution2.add("--q-");
        expectedSolution.add(solution2);



        ArrayList<List<String>> actual = NQueens.nQueens(n);

        assertThat(actual).containsExactlyInAnyOrder(newArrayList(solution1),newArrayList(solution2));
    }
}