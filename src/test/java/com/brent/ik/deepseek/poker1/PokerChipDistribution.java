package com.brent.ik.deepseek.poker1;

import java.util.*;

public class PokerChipDistribution {

    public static void main(String[] args) {
        // Input data
        double targetBuyIn = 31.0;
        int numPlayers = 10;
        Map<String, Integer> availableChips = new HashMap<>();
        availableChips.put("White", 149);
        availableChips.put("Red", 143);
        availableChips.put("Blue", 98);
        availableChips.put("Green", 50);
        availableChips.put("Black", 46);
        List<Double> denominations = Arrays.asList(0.25, 0.5, 1.0, 2.0, 5.0, 10.0);

        // Assign denominations to chip colors
        Map<String, Double> chipDenominations = assignDenominations(availableChips, denominations);

        // Distribute chips to players
        Map<String, Integer> chipDistribution = distributeChips(targetBuyIn, numPlayers, chipDenominations, availableChips);

        // Output the results
        System.out.println("Chip Denominations: " + chipDenominations);
        System.out.println("Chip Distribution per Player: " + chipDistribution);
    }

    private static Map<String, Double> assignDenominations(Map<String, Integer> availableChips, List<Double> denominations) {
        // Sort chip colors by count in descending order
        List<Map.Entry<String, Integer>> sortedChips = new ArrayList<>(availableChips.entrySet());
        sortedChips.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        // Sort denominations in ascending order
        denominations.sort(Double::compareTo);

        // Assign smallest denominations to most abundant chips
        Map<String, Double> chipDenominations = new HashMap<>();
        for (int i = 0; i < sortedChips.size(); i++) {
            chipDenominations.put(sortedChips.get(i).getKey(), denominations.get(i));
        }

        return chipDenominations;
    }

    private static Map<String, Integer> distributeChips(double targetBuyIn, int numPlayers, Map<String, Double> chipDenominations, Map<String, Integer> availableChips) {
        // Convert target buy-in to cents to avoid floating-point inaccuracies
        int targetCents = (int) (targetBuyIn * 100);

        // Sort chip denominations in ascending order
        List<Map.Entry<String, Double>> sortedDenominations = new ArrayList<>(chipDenominations.entrySet());
        sortedDenominations.sort(Comparator.comparingDouble(Map.Entry::getValue));

        // Initialize chip distribution per player
        Map<String, Integer> chipDistribution = new HashMap<>();
        for (String color : chipDenominations.keySet()) {
            chipDistribution.put(color, 0);
        }

        // Distribute chips starting from the smallest denomination
        for (Map.Entry<String, Double> entry : sortedDenominations) {
            String color = entry.getKey();
            double denomination = entry.getValue();
            int denominationCents = (int) (denomination * 100);

            // Calculate the maximum number of chips of this color that can be given to each player
            int maxChipsPerPlayer = targetCents / denominationCents;
            int totalChipsNeeded = maxChipsPerPlayer * numPlayers;

            // Check if there are enough chips available
            int availableChipsForColor = availableChips.get(color);
            if (totalChipsNeeded > availableChipsForColor) {
                maxChipsPerPlayer = availableChipsForColor / numPlayers;
            }

            // Update the chip distribution and remaining target amount
            chipDistribution.put(color, maxChipsPerPlayer);
            targetCents -= maxChipsPerPlayer * denominationCents * numPlayers;
        }

        // If there's still a remaining amount, it means we couldn't distribute the exact amount
        if (targetCents > 0) {
            System.out.println("Warning: Could not distribute the exact amount. Remaining: " + targetCents + " cents.");
        }

        return chipDistribution;
    }
}