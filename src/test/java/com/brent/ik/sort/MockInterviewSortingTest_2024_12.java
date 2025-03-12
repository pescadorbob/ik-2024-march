package com.brent.ik.sort;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class MockInterviewSortingTest_2024_12 {
    public static Stream<Arguments> shouldReturnMinimumNumberOfDeletionsFromEachStringRequiredToMakeTheArrayOfStringsAppearInLexicographicOrder_givenAnArrayOfString() {
        return Stream.of(
                Arguments.of(asList("ca", "bb", "ac").toArray(new String[]{}), 1),
                Arguments.of(asList("abzdn", "abydm", "abxdl").toArray(new String[]{}), 2),
                Arguments.of(asList("abzzdn", "abyydm", "abxxdl").toArray(new String[]{}), 3)
        );
    }

    /*
        Given an array of strings, you may delete characters from the same index for each string.
     Return the minimum number of deletions from each string required to make the array of strings appear in lexicographic order
         */
    @ParameterizedTest
    @MethodSource
    void shouldReturnMinimumNumberOfDeletionsFromEachStringRequiredToMakeTheArrayOfStringsAppearInLexicographicOrder_givenAnArrayOfString(String[] input, int expected) {
        var actual = minDeletionSize(input);
        assertThat(actual).isEqualTo(expected);
    }

    public int minDeletionSize(String[] strs) {
        int n = strs.length;
        int m = strs[0].length();
        int deletions = 0;
        boolean[] keep = new boolean[m];

        for (int j = 0; j < m; j++) {
            if (canKeepColumn(strs, j, keep)) {
                keep[j] = true;
            } else {
                deletions++;
            }
        }

        return deletions;
    }

    private boolean canKeepColumn(String[] strs, int col, boolean[] keep) {
        for (int i = 1; i < strs.length; i++) {
            int cmp = compare(strs[i-1], strs[i], col, keep);
            if (cmp > 0) return false;
            if (cmp < 0) return true;
        }
        return true;
    }

    private int compare(String s1, String s2, int col, boolean[] keep) {
        for (int j = 0; j < col; j++) {
            if (keep[j]) {
                if (s1.charAt(j) != s2.charAt(j)) {
                    return s1.charAt(j) - s2.charAt(j);
                }
            }
        }
        return s1.charAt(col) - s2.charAt(col);
    }
}
