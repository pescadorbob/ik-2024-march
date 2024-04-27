package com.brent.ik.dp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class WordBreakTest {
    boolean wordBreak(String[] dict, String w) {
        System.out.printf("Word [%s] len:%d%n", w, w.length());
        boolean[][] T = new boolean[w.length()][w.length()];
        Set<String> hashDict = new HashSet<>(List.of(dict));
        int n = w.length();
        int length = 1;
        for (int row = 0; row < n; ++row) {
            length++;
            System.out.printf("Length %d%n", length);
            for (int col = 0; col < n - row; ++col) {
                int i = col;
                int j = row + col;

                // try all partitions k of the substring i to j
                // such that T[i][k] && T[k+1][j]
                // note that these are the results of the smaller
                // sub-problems that would result in getting this
                // incrementally bigger problem to be true
                // if any of these became bigger.

                System.out.println(i + "," + j);
                if (hashDict.contains(w.substring(i, j + 1))) {
                    T[i][j] = true;
                    System.out.printf("%s is in dictionary%n", w.substring(i, j + 1));
                } else {
                    System.out.printf("%s is not dictionary%n", w.substring(i, j + 1));
                    for (int k = i; k < j; k++) {
                        System.out.printf("Checking %s,%s substrings were built of substrings in the dictionary %b:%b%n", w.substring(i, k + 1), w.substring(k + 1, j + 1), T[i][k], T[k + 1][j]);
                        if (T[i][k] && T[k + 1][j]) {
                            T[i][j] = true;
                            break;
                        }
                    }
                }
            }
        }
        prettyPrintMatrix(T);
        return T[0][n - 1];
    }

    public static void prettyPrintMatrix(boolean[][] matrix) {
        for (boolean[] booleans : matrix) {
            for (boolean aBoolean : booleans) {
                // Print "T" for true and "F" for false, followed by a space
                System.out.print(aBoolean ? "T " : "F ");
            }
            // Move to the next line after each row
            System.out.println();
        }
    }


    @ParameterizedTest
    @MethodSource
    void shouldReturnTrue_givenHelloWorldHello(String s, String[] dict, boolean expected) {
        assertThat(wordBreak(dict, s)).isEqualTo(expected);
    }

    private static Stream<Arguments> shouldReturnTrue_givenHelloWorldHello() {
        return Stream.of(
                Arguments.of("iamace",
                        new String[]{"i", "am", "ace", "a"}, true),
                Arguments.of("helloworldhello",
                        new String[]{"hello", "world"}, true)
        );
    }

    @Test
    void shouldPrintHalfAMatrix() {
        int[][] matrix = new int[][]{
                {1, 2, 3, 4, 5},
                {11, 12, 13, 14, 15},
                {21, 22, 23, 24, 25},
                {31, 32, 33, 34, 35},
                {41, 42, 43, 44, 45},
        };
        List<List<Integer>> expected = new ArrayList<>();
        var row1= new ArrayList<Integer>();
        row1.add(1);
        row1.add(12);
        row1.add(23);
        row1.add(34);
        row1.add(45);
        expected.add(row1);
        var row2 = new ArrayList<Integer>();
        row2.add(2);
        row2.add(13);
        row2.add(24);
        row2.add(35);
        expected.add(row2);
        var row3 = new ArrayList<Integer>();
        row3.add(3);
        row3.add(14);
        row3.add(25);
        expected.add(row3);
        var row4 = new ArrayList<Integer>();
        row4.add(4);
        row4.add(15);
        expected.add(row4);
        var row5 = new ArrayList<Integer>();
        row5.add(5);
        expected.add(row5);
        var actual = new ArrayList<ArrayList<Integer>>();
        for (int row = 0; row < matrix.length; ++row) {
            var currRow=new ArrayList<Integer>();
            for (int col = 0; col < matrix.length - row; ++col) {
                int i = col;
                int j = row + col;
                currRow.add(matrix[i][j]);
                System.out.printf("%d-", matrix[i][j]);
            }
            actual.add(currRow);
            System.out.println();
        }
        assertThat(actual).isEqualTo(expected);
    }

}
