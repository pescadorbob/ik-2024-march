package com.brent.ik.dp;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class ChipDenominationCombinationsShould {

    public static Stream<Arguments> inputs() {
        return Stream.of(
                Arguments.of(
                        asList("Black", "Blue"), asList(.10, .25),
                        asList(
                                asList(new Coin("Black", .10),
                                        new Coin("Blue", .25)),
                                asList(new Coin("Blue", .10),
                                        new Coin("Black", .25)))),                Arguments.of(
                        asList("Black", "Blue"), asList(.10, .25,.50),
                        asList(
                                asList(new Coin("Black", .10),
                                        new Coin("Blue", .25)),
                                asList(new Coin("Black", .10),
                                        new Coin("Blue", .50)),
                                asList(new Coin("Black", .25),
                                        new Coin("Blue", .10)),
                                asList(new Coin("Black", .25),
                                        new Coin("Blue", .50)),
                                asList(new Coin("Black", .50),
                                        new Coin("Blue", .10)),
                                asList(new Coin("Black", .50),
                                        new Coin("Blue", .25))
                        ))
        );
    }

    @ParameterizedTest
    @MethodSource("inputs")
    void printAllCombinationsZeroToNine_givenLength(List<String> colors,
                                                    List<Double> denominations,
                                                    List<List<Coin>> expected) {


        var actual = chipValueCombinations(colors, denominations);


        assertListsAreEquivalentIgnoringOrder(actual, expected);
    }

    private static void assertListsAreEquivalentIgnoringOrder(List<List<Coin>> actual, List<List<Coin>> expected) {
        assertThat(actual)
                .hasSize(expected.size())
                .allSatisfy(sublist1 ->
                        assertThat(expected)
                                .anySatisfy(subList2 ->
                                        assertThat(sublist1)
                                                .containsExactlyInAnyOrderElementsOf(subList2)));
    }

    private List<List<Coin>> chipValueCombinations(List<String> colors, List<Double> denominations) {
        var slate = new ArrayList<List<Coin>>();
        var partialSolution = new ArrayList<Coin>();
        helper(colors, 0, denominations, slate, partialSolution, new boolean[denominations.size()]);
        return slate;
    }

    private void helper(List<String> colors, int colorIndex, List<Double> denominations,
                        List<List<Coin>> slate, List<Coin> partialSolution, boolean[] usedDenominations) {

        if (colorIndex == colors.size()) {
            slate.add(new ArrayList<>(partialSolution));
            return;
        }
        for (int i = 0; i < denominations.size(); i++) {
            if (!usedDenominations[i]) {
                usedDenominations[i] = true;
                partialSolution.add(new Coin(colors.get(colorIndex), denominations.get(i)));
                helper(colors, colorIndex + 1, denominations, slate, partialSolution, usedDenominations);
                partialSolution.remove(partialSolution.size() - 1);
                usedDenominations[i] = false;
            }
        }
    }

    private boolean colorNotUsed(List<Coin> prev, String color) {
        return prev.stream().filter(coin -> coin.getColor().compareTo(color) == 0).toList().isEmpty();
    }


    public static Stream<Arguments> color() {
        return Stream.of(
                Arguments.of(asList(new Coin("Black", .10)), "Blue", true),
                Arguments.of(asList(new Coin("Black", .10),
                        new Coin("Red", .25)), "Blue", true),
                Arguments.of(asList(new Coin("Black", .10)), "Black", false),
                Arguments.of(asList(new Coin("Black", .10),
                        new Coin("Red", .25)), "Black", false)
        );
    }

    @ParameterizedTest
    @MethodSource("color")
    void test_color_not_used(List<Coin> coins, String color, boolean expected) {

        var actual = colorNotUsed(coins, color);

        assertThat(actual).isEqualTo(expected);
    }

}
