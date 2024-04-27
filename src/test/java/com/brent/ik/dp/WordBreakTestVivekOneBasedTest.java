package com.brent.ik.dp;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class WordBreakTestVivekOneBasedTest {
    private static Stream<Arguments> shouldReturnTrue_givenHelloWorldHello() {
        return Stream.of(
                Arguments.of("iamace",
                        new String[]{"i", "am", "ace", "a"}, true),
                Arguments.of("helloworldhello",
                        new String[]{"hello", "world"}, true)
        );
    }

    boolean wordBreak(String[] dict, String s) {
        int n = s.length();
        boolean[] T = new boolean[n + 1];
        T[0] = true;
        var dictSet = new HashSet<>(List.of(dict));
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                if (T[j] && dictSet.contains(s.substring(j, i))) {
                    T[i] = true;
                    break;
                }
            }
        }
        return T[n];
    }

    @ParameterizedTest
    @MethodSource
    void shouldReturnTrue_givenHelloWorldHello(String s, String[] dict, boolean expected) {
        assertThat(wordBreak(dict, s)).isEqualTo(expected);
    }


}
