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
        int n = 5;
        String[][] expected = new String[][]{
                {"q----", "--q--", "----q", "-q---", "---q-"},
{"q----", "---q-", "-q---", "----q", "--q--"},
{"-q---", "---q-", "q----", "--q--", "----q"},
{"-q---", "----q", "--q--", "q----", "---q-"},
{"--q--", "q----", "---q-", "-q---", "----q"},
{"--q--", "----q", "-q---", "---q-", "q----"},
{"---q-", "q----", "--q--", "----q", "-q---"},
{"---q-", "-q---", "----q", "--q--", "q----"},
{"----q", "-q---", "---q-", "q----", "--q--"},
{"----q", "--q--", "q----", "---q-", "-q---"}
};
        List<List<String>> expectedSolution = Arrays.stream(expected).map(soln-> Arrays.stream(soln).map(String::new).toList()).toList();




        List<List<String>> actual = NQueens.nQueens(n);

        assertThat(actual).hasSameElementsAs(expectedSolution);
    }
}