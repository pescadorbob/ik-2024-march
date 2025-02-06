package com.brent.nerds.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static java.lang.Math.min;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class MinimumMovesCalculatorShould {
    public static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(asList(1, 2, 3), 3),
                Arguments.of(asList(7,10,10), 6)
        );
    }

    @ParameterizedTest
    @MethodSource("input")
    void find_the_minimum_moves_for_n_minus_1_items(List<Integer> input, int expected) {

        var actual = calculateMinimumMoves(input);

        assertThat(actual).isEqualTo(expected);
    }

    private int calculateMinimumMoves(List<Integer> input) {
        int minIndex = minIndex(input);
        // add all the differences now for all the other elements
        int moves = 0;
        for (int i = 0; i < input.size(); i++) {
            if (i != minIndex) {
                moves += input.get(i) - input.get(minIndex);
            }
        }
        return moves;
    }

    private int minIndex(List<Integer> input) {
        int min = input.get(0);
        int minIndex = 0;
        for (int i = 0; i < input.size(); i++) {
            int before = min;
            min = min(min, input.get(i));
            if (before != min) {
                minIndex = i;
            }
        }
        return minIndex;
    }
}
