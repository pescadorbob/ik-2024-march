package com.brent.ik.combinations;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.brent.ik.combinations.ChipDenominationCombinations.chipValueCombinations;
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


}
