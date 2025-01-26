package com.brent.ik.recursive;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

import static com.brent.ik.recursive.PokerSetTestBuilder.aPokerSet;

@DisplayName("Poker Chip Distributor Should")
public class PokerChipDistributorShould {

    @Test
    @DisplayName("maximize the number of chips for each player given the chips available, possible denominations, and number of players")
    void maximize_the_number_of_chips_for_each_player_given_the_chips_available_possible_denominations_and_number_of_players(){
        var pokerSet = aPokerSet()
                .withChips("White",20)
                .withChips("Red",20)
                .withChips("Green",30)
                .withChips("Blue",25)
                .withChips("Black",15)
                .build();

        var numPlayers = 10;
        var targetTotal = 300d;
        double [] possibleDenominations = {.01d,.05d,.10,.25,.50,1,2,5,10};
    }

    public static void main(String[] args) {
        Map<String, Integer> chipColors = new HashMap<>();
        chipColors.put("Red", 20);  // Number of red chips
        chipColors.put("Green", 30);  // Number of green chips
        chipColors.put("Blue", 25);  // Number of blue chips
        chipColors.put("Black", 15);  // Number of black chips

        Integer[] possibleDenominations = {1, 5, 10, 25};  // Possible denominations

        int setPlayerValue = 300;  // Set value each player should receive
        int players = 4;  // Number of players

        Map<String, Integer> chipDenominations = assignDenominations(chipColors.keySet(), possibleDenominations);
        if (chipDenominations != null) {
            System.out.println("Denominations assigned to each chip color:");
            for (Map.Entry<String, Integer> entry : chipDenominations.entrySet()) {
                System.out.println("  " + entry.getKey() + ": $" + entry.getValue());
            }
        }

        Map<String, Integer[][]> distributedChips = distributeChips(chipColors, chipDenominations, setPlayerValue, players);

        if (distributedChips != null) {
            // Printing the distributed chips for each player
            for (int i = 0; i < players; i++) {
                System.out.println("Player " + (i + 1) + ":");
                for (Map.Entry<String, Integer[][]> entry : distributedChips.entrySet()) {
                    System.out.println("  " + entry.getKey() + ": " + Arrays.toString(entry.getValue()[i]));
                }
            }
        } else {
            System.out.println("Cannot distribute chips evenly among players.");
        }
    }

    public static Map<String, Integer> assignDenominations(Set<String> chipColors, Integer[] possibleDenominations) {
        Map<String, Integer> chipDenominations = new HashMap<>();
        Iterator<Integer> denominationIterator = Arrays.asList(possibleDenominations).iterator();

        for (String color : chipColors) {
            if (denominationIterator.hasNext()) {
                chipDenominations.put(color, denominationIterator.next());
            } else {
                return null; // Not enough denominations for all colors
            }
        }

        return chipDenominations;
    }

    public static Map<String, Integer[][]> distributeChips(Map<String, Integer> chipColors, Map<String, Integer> chipDenominations, int setPlayerValue, int players) {
        Map<String, Integer[][]> playerChips = new HashMap<>();
        for (String color : chipColors.keySet()) {
            playerChips.put(color, new Integer[players][1]);
            for (int i = 0; i < players; i++) {
                playerChips.get(color)[i][0] = 0;
            }
        }

        int totalValueNeeded = setPlayerValue * players;

        if (chipDenominations == null) {
            return null; // No valid denomination assignment found
        }

        for (String color : chipColors.keySet()) {
            int denomination = chipDenominations.get(color);
            int totalChips = chipColors.get(color);

            int chipsNeededPerPlayer = setPlayerValue / denomination;
            int totalChipsNeeded = chipsNeededPerPlayer * players;

            if (totalChips < totalChipsNeeded) {
                // If not enough chips, distribute as many as possible
                chipsNeededPerPlayer = totalChips / players;
            }

            for (int j = 0; j < players; j++) {
                playerChips.get(color)[j][0] = chipsNeededPerPlayer;
            }
        }

        return playerChips;
    }
}
