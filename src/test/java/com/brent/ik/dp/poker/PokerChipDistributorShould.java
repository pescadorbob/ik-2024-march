package com.brent.ik.dp.poker;

import com.brent.ik.combinations.Coin;
import com.brent.ik.combinations.PokerSet;
import com.brent.ik.combinations.PokerSetSolution;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.brent.ik.combinations.ChipDenominationCombinations.chipValueCombinations;
import static com.brent.ik.dp.poker.PokerSetSolutionTestBuilder.aPokerSetSolution;
import static com.brent.ik.dp.poker.PokerSetTestBuilder.aPokerSet;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Poker Chip Distributor Should")
public class PokerChipDistributorShould {

    public static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(aPokerSet()
                                .withChips("White", 149)
                                .withChips("Red", 143)
                                .withChips("Blue", 98)
                                .withChips("Black", 46)
                                .withChips("Green", 50)
                                .build(), 10, 31d, asList(.25, .50, 1.0d, 2.0d, 5.0d, 10.0d),
                        aPokerSetSolution()
                                .withTotal(410)
                                .withCoinAmount(new Coin("White", 1.0), 13)
                                .withCoinAmount(new Coin("Red", .25), 14)
                                .withCoinAmount(new Coin("Blue", .50), 9)
                                .withCoinAmount(new Coin("Black", 5.0), 0)
                                .withCoinAmount(new Coin("Green", 2.0), 5)
                                .build()),
                Arguments.of(aPokerSet()
                                .withChips("White", 20)
                                .withChips("Red", 15).build(),
                        1, 10d, asList(.50d, 1d),
                        aPokerSetSolution()
                                .withTotal(20)
                                .withCoinAmount(new Coin("White", .50), 20)
                                .withCoinAmount(new Coin("Red", 1.0), 0)
                                .build()),
                Arguments.of(aPokerSet()
                                .withChips("White", 40)
                                .withChips("Red", 30).build(),
                        2, 10d, asList(.50d, 1d),
                        aPokerSetSolution()
                                .withTotal(40)
                                .withCoinAmount(new Coin("White", .50), 20)
                                .withCoinAmount(new Coin("Red", 1.0), 0)
                                .build())

        );
    }

    @ParameterizedTest
    @DisplayName("maximize the number of chips for each player given the chips available, possible denominations, and number of players")
    @MethodSource("input")
    void maximize_the_number_of_chips_for_each_player_given_the_chips_available_possible_denominations_and_number_of_players(
            PokerSet pokerset, int numPlayers, double targetTotal, List<Double> possibleDenominations, PokerSetSolution expectedResults
    ) {
        var actual = distributeChips(pokerset, possibleDenominations, targetTotal, numPlayers);

        assertThat(actual).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedResults);
    }

    private PokerSetSolution distributeChips(PokerSet pokerset, List<Double> possibleDenominations, double targetTotal, int numPlayers) {
        var combinations = chipValueCombinations(pokerset.getChips().keySet().stream().toList(), possibleDenominations);
        PokerSetSolution bestSolution = null;
        for (int i = 0; i < combinations.size(); i++) {
            List<Coin> chipValueCombination = combinations.get(i);
            var clonedPokerSet = aPokerSet().from(pokerset).build();
            var solution = calculateMaxChipsForTargetBuyInCoinsAndNumPlayers(clonedPokerSet, chipValueCombination, targetTotal, numPlayers);
            if (bestSolution == null) {
                bestSolution = solution;
            } else {
                if (bestSolution.getTotalchips() <= solution.getTotalchips()) {
                    System.out.println("A best solution found with " + solution.getTotalchips());
                    printSolution(solution);
                }
                if (bestSolution.getTotalchips() < solution.getTotalchips()) {
                    bestSolution = solution;
                }
            }
        }

        return bestSolution;
    }

    private void printSolution(PokerSetSolution bestDistribution) {
        if (bestDistribution == null) {
            System.out.println("No valid distribution found!");
            return;
        }

        System.out.println("\nOptimal Distribution Found:");
        System.out.printf("Total chips per player: %d%n", bestDistribution.getTotalchips());
        System.out.printf("Total value: $%.2f%n", bestDistribution.getTotalValue());
        System.out.println("\nChip Distribution:");

        // Print in a formatted table
        System.out.println("Color\t Denomination \t# of chips\t Player Total");
        for (Coin coin : bestDistribution.getCoinAmount().keySet()) {
            int count = bestDistribution.getCoinAmount().get(coin);
            double denom = coin.getDenomination();
            double total = count * denom;
            System.out.printf("%s\t $%.2f \t%d\t $%.2f%n",
                    coin.getColor(), denom, count, total);
        }
        System.out.printf("Total\t\t%d\t $%.2f%n",
                bestDistribution.getTotalchips(),
                bestDistribution.getTotalValue());

    }

    private PokerSetSolution calculateMaxChipsForTargetBuyInCoinsAndNumPlayers(PokerSet pokerSet, List<Coin> chipValueCombination, double targetTotal, int numPlayers) {
        var solution = new PokerSetSolution();
        var partialSolution = new ArrayList<Coin>();
        helper(chipValueCombination, targetTotal, numPlayers, solution, partialSolution, pokerSet);
        return solution;
    }

    private static final class IndexSolution {
        int maxCoins;
        int originIndex;
        Coin originIndexCoin;
        double balance;

        @Override
        public String toString() {
            return "IndexSolution{" +
                    "maxCoins=" + maxCoins +
                    ", originIndex=" + originIndex +
                    ", originIndexCoin=" + originIndexCoin +
                    ", balance=" + balance +
                    '}';
        }
    }

    private void helper(List<Coin> chipValueCombination, double targetTotal, int numPlayers, PokerSetSolution solution,
                        List<Coin> partial,
                        PokerSet pokerSet) {
        var minDenomination = minDenomination(chipValueCombination);

        var memoryTable = new IndexSolution[(int) (targetTotal / minDenomination) + 1];

        memoryTable[0] = new IndexSolution();
        memoryTable[0].maxCoins = 0;
        for (int i = 1; i < memoryTable.length; i++) {
            memoryTable[i] = null;
        }
        for (int i = 1; i < targetTotal / minDenomination + 1; i++) {
            //compute and cache f(i)
            var maxCoins = 0;
            Coin usedCoin = null;
            Integer backIndex = null;
            for (Coin c : chipValueCombination) {
                if (i - c.getDenomination() / minDenomination >= 0
                        && coinsLeft(pokerSet, memoryTable, i - (int) (c.getDenomination() / minDenomination), c, numPlayers)) {
                    var beforeValue = maxCoins;
                    maxCoins = max(maxCoins, memoryTable[i - (int) (c.getDenomination() / minDenomination)].maxCoins);
                    if (beforeValue != maxCoins || usedCoin == null) {
                        usedCoin = c;
                        backIndex = i - (int) (c.getDenomination() / minDenomination);
                    }
                }
            }
            if (usedCoin != null) {

                var indexSolution = new IndexSolution();
                indexSolution.maxCoins = maxCoins + numPlayers;
                indexSolution.originIndexCoin = usedCoin;
                indexSolution.originIndex = backIndex;
                if (backIndex >= 0) {
                    var prevCoin = memoryTable[backIndex];
                    indexSolution.balance = usedCoin.getDenomination() + prevCoin.balance;
                }
                memoryTable[i] = indexSolution;


            }
        }
        calculateSolutionCoins(solution, memoryTable, chipValueCombination, numPlayers, pokerSet);
    }

    private void calculateSolutionCoins(PokerSetSolution solution, IndexSolution[] memoryTable, List<Coin> chipValueCombination, Integer numPlayers, PokerSet pokerSet) {
        var solutionNode = memoryTable[memoryTable.length - 1];
        if (solutionNode != null) {
            solution.setTotalchips(solutionNode.maxCoins);
            chipValueCombination.forEach(coin -> {

                Integer coinAmount = countCoins(solutionNode, coin, memoryTable);
                solution.getCoinAmount().put(coin, coinAmount);
            });
        } else {
            System.out.println("No solution found for chipValueCombination " + chipValueCombination);
            System.out.println("Poker set:" + pokerSet);
            printSolutionGraph(memoryTable);

        }
    }

    private void printSolutionGraph(IndexSolution[] memoryTable) {
        for (int i = 0; i < memoryTable.length; i++) {
            System.out.println("[" + i + "]:" + memoryTable[i]);
        }
    }

    private Integer countCoins(IndexSolution solutionNode, Coin coin, IndexSolution[] memoryTable) {
        var currNode = solutionNode;
        int count = 0;
        while (currNode.originIndexCoin != null) {
            if (currNode.originIndexCoin.equals(coin)) {
                count++;
            }
            currNode = memoryTable[currNode.originIndex];
        }
        return count;
    }

    private boolean coinsLeft(PokerSet pokerSet, IndexSolution[] memoryTable, int memTableIndex, Coin c, Integer numPlayers) {
        int coinsInPath = countCoins(memoryTable[memTableIndex], c, memoryTable);
        return pokerSet.getChips().get(c.getColor()) - numPlayers * coinsInPath - numPlayers >= 0;
    }


    private Double minDenomination(List<Coin> values) {
        var min = Double.MAX_VALUE;
        for (Coin c : values) {
            min = min(c.getDenomination(), min);
        }
        return min;

    }

}
