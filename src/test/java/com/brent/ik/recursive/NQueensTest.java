package com.brent.ik.recursive;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NQueensTest {

    @Test
    void nQueens() {
        int n = 4;
        var expectedSolution =
                Arrays.asList(
                        Arrays.asList("--q-",
                                "q---",
                                "---q",
                                "-q--")

                        , Arrays.asList("-q--",
                                "---q",
                                "q---",
                                "--q-")
                );

    var actual = NQueens.nQueens(n);

    assertThat(expectedSolution).

    isEqualTo(actual);
}
}