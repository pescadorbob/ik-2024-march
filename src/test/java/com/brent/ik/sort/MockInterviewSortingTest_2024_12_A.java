package com.brent.ik.sort;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class MockInterviewSortingTest_2024_12_A {
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

    /*
    This solution works as follows:
    We iterate through each column of the strings.
    For each column, we check if it's in lexicographic order.
    If a column is not in lexicographic order, we increment the deletion count.
    We return the total number of deletions needed.
    The isColumnLexicographic method checks if a specific column is in lexicographic order by comparing each character with the one in the string above it.
    This solution correctly handles both of your example cases:
    For ["ca", "bb", "ac"], it will return 1, as we need to delete the first column.
    For ["abzdn", "abydm", "abxdl"], it will return 2, as we need to delete the 3rd and 4th columns (index 2 and 3).
    The time complexity of this solution is O(n * m), where n is the number of strings and m is the length of each string. This is more efficient than the previous solution and directly solves the problem as described.
     */
    public int minDeletionSize(String[] strs) {
        int deletions = 0;
        int strLen = strs[0].length();

        for (int col = 0; col < strLen; col++) {
            if (!isColumnLexicographic(strs, col)) {
                deletions++;
            }
        }

        return deletions;
    }

    private boolean isColumnLexicographic(String[] strs, int col) {
        for (int i = 1; i < strs.length; i++) {
            if (strs[i].charAt(col) < strs[i-1].charAt(col)) {
                return false;
            }
        }
        return true;
    }
}
