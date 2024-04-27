package com.brent.ik.dp;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class WordBreakTest {
    boolean wordBreak(String[] dict, String w) {
        System.out.printf("Word [%s] len:%d%n", w, w.length());
        boolean[][] T = new boolean[w.length()][w.length()];
        Set<String> hashDict = new HashSet<>(List.of(dict));
        int n = w.length();
        int length = 1;
        for (int x = 0; x < 2 * n; x++) {
            length++;
            System.out.printf("Length %d%n", length);
            for (int i = 0; i < n - x; i++) {
                int j = i + x;
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

    @Test
    void shouldReturnTrue_givenHelloWorldHello() {
        String s = "iamace";
        String[] dict = new String[]{"i", "am", "ace", "a"};
        assertThat(wordBreak(dict, s)).isTrue();
    }
}
