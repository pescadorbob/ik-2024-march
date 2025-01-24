package com.brent.ik.recursive;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import java.util.*;

public class PokerChipDistributor {

    public static void main(String[] args) {
        Map<String, Integer> chipColors = new HashMap<>();
        chipColors.put("Red", 20);  // Number of red chips
        chipColors.put("Green", 30);  // Number of green chips
        chipColors.put("Blue", 25);  // Number of blue chips
        chipColors.put("Black", 15);  // Number of black chips

        Map<String, Integer[]> chipDenominations = new HashMap<>();
        chipDenominations.put("Red", new Integer[]{1, 5, 10});  // Denominations for red chips
        chipDenominations.put("Green", new Integer[]{2, 6, 15});  // Denominations for green chips
        chipDenominations.put("Blue", new Integer[]{3, 8, 20});  // Denominations for blue chips
        chipDenominations.put("Black", new Integer[]{4, 10, 25});  // Denominations for black chips

        int setPlayerValue = 80;  // Set value each player should receive
        int players = 4;  // Number of players

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

    public static Map<String, Integer[][]> distributeChips(Map<String, Integer> chipColors, Map<String, Integer[]> chipDenominations, int setPlayerValue, int players) {
        Map<String, Integer[][]> playerChips = new HashMap<>();
        for (String color : chipColors.keySet()) {
            playerChips.put(color, new Integer[players][chipDenominations.get(color).length]);
        }

        int totalValueNeeded = setPlayerValue * players;
        int totalChipsValue = 0;

        for (String color : chipColors.keySet()) {
            Integer[] values = chipDenominations.get(color);
            for (int i = 0; i < values.length; i++) {
                totalChipsValue += chipColors.get(color) * values[i];
            }
        }

        if (totalChipsValue < totalValueNeeded) {
            return null; // Not enough chips to meet the set value for each player
        }

        for (String color : chipColors.keySet()) {
            Integer[] values = chipDenominations.get(color);
            int totalChips = chipColors.get(color);

            for (int i = 0; i < players; i++) {
                Arrays.fill(playerChips.get(color)[i], 0);
            }

            for (int i = values.length - 1; i >= 0; i--) {
                int chipsNeededPerPlayer = setPlayerValue / values[i];
                int totalChipsNeeded = chipsNeededPerPlayer * players;

                if (totalChips >= totalChipsNeeded) {
                    for (int j = 0; j < players; j++) {
                        playerChips.get(color)[j][i] = chipsNeededPerPlayer;
                    }
                    totalChips -= totalChipsNeeded;
                } else {
                    int chipsPerPlayer = totalChips / players;
                    for (int j = 0; j < players; j++) {
                        playerChips.get(color)[j][i] = chipsPerPlayer;
                    }
                    totalChips -= chipsPerPlayer * players;
                }
            }
        }

        return playerChips;
    }
}

