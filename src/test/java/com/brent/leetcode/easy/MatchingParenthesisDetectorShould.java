package com.brent.leetcode.easy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class MatchingParenthesisDetectorShould {
    public static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of("([])",true),
                Arguments.of("]]]",false)
        );
    }

    @ParameterizedTest
    @MethodSource("input")
    void detect_valid_parenthesis_given_valid_parenthesis(String input, boolean expected) {


        var actual = isValid(input);

        assertThat(actual).isEqualTo(expected);
    }

    private static final List<Character> opens = Arrays.asList('(', '{', '[');
    private static final List<Character> closes = Arrays.asList(')', '}', ']');

    public boolean isValid(String s) {
        var parenthesisStack = new ArrayDeque<Character>();

        for (int i = 0; i < s.length(); i++) {
            var c = s.charAt(i);
            if (opens.contains(c)) {
                parenthesisStack.push(c);
            } else {
                if(parenthesisStack.isEmpty()) return false;
                var leftMatchCandidate = parenthesisStack.pop();
                if (!isLeftMatchingRight(leftMatchCandidate, c)) return false;

            }
        }
        return parenthesisStack.isEmpty();
    }

    public static boolean isLeftMatchingRight(char l, char r) {

        var lIndex = opens.indexOf(l);
        var rIndex = closes.indexOf(r);
        return lIndex == rIndex;
    }
}

