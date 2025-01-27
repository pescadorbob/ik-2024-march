package com.brent.ik.dp;

import com.brent.ik.combinations.Coin;
import com.brent.ik.combinations.PokerSet;
import com.brent.ik.combinations.PokerSetSolution;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static com.brent.ik.dp.PokerSetSolutionTestBuilder.aPokerSetSolution;
import static com.brent.ik.dp.PokerSetTestBuilder.aPokerSet;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Poker Chip Distributor Should")
public class PokerChipDistributorShould {

    public static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(aPokerSet()
                        .withChips("White", 20)
                        .withChips("Red", 20)
                        .withChips("Green", 30)
                        .withChips("Blue", 25)
                        .withChips("Black", 15)
                        .build(), 10, 300, asList(.01d, .05d, .10, .25, .50, 1, 2, 5, 10),
                        aPokerSetSolution().withCoinAmount(new Coin("White", .50), 20).build()),
                Arguments.of(aPokerSet()
                                .withChips("White", 20)
                                .withChips("Red", 15).build(),
                        1, 10d, asList(.50, 1),
                        aPokerSetSolution().withCoinAmount(new Coin("White", .50), 20).build())

        );
    }

    @ParameterizedTest
    @DisplayName("maximize the number of chips for each player given the chips available, possible denominations, and number of players")
    @MethodSource("input")
    void maximize_the_number_of_chips_for_each_player_given_the_chips_available_possible_denominations_and_number_of_players(
            PokerSet pokerset, int numPlayers, double targetTotal, List<Double> possibleDenominations, PokerSetSolution expectedResults
    ) {
        var actual = distributeChips(pokerset,possibleDenominations,targetTotal,numPlayers);

        assertThat(actual).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedResults);
    }

    private PokerSetSolution distributeChips(PokerSet pokerset, List<Double> possibleDenominations, double targetTotal, int numPlayers) {
        return null;
    }

}
