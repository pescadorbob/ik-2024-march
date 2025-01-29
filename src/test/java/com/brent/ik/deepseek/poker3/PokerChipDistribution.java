package com.brent.ik.deepseek.poker3;

import java.util.*;

public class PokerChipDistribution {

    // Define chip colors and their available quantities
    private static final Map<String, Integer> chipQuantities = new HashMap<>();
    static {
        chipQuantities.put("White", 149);
        chipQuantities.put("Red", 143);
        chipQuantities.put("Blue", 98);
        chipQuantities.put("Green", 50);
        chipQuantities.put("Black", 46);
    }

    // Define possible denominations in cents
    private static final int[] denominations = {25, 50, 100, 200, 500, 1000};

    public static void main(String[] args) {
        int targetBuyIn = 3100; // $31 in cents
        int numPlayers = 10;

        // Generate all possible assignments of denominations to chip colors
        List<Map<String, Integer>> assignments = generateAssignments();

        // Find the optimal assignment
        Map<String, Integer> optimalAssignment = findOptimalAssignment(assignments, targetBuyIn, numPlayers);

        if (optimalAssignment != null) {
            System.out.println("Optimal Assignment: " + optimalAssignment);
            distributeChips(optimalAssignment, targetBuyIn, numPlayers);
        } else {
            System.out.println("No valid assignment found.");
        }
    }

    private static List<Map<String, Integer>> generateAssignments() {
        List<Map<String, Integer>> assignments = new ArrayList<>();
        List<String> colors = new ArrayList<>(chipQuantities.keySet());
        permute(assignments, colors, 0);
        return assignments;
    }

    private static void permute(List<Map<String, Integer>> assignments, List<String> colors, int start) {
        if (start == colors.size() - 1) {
            Map<String, Integer> assignment = new HashMap<>();
            for (int i = 0; i < colors.size(); i++) {
                assignment.put(colors.get(i), denominations[i]);
            }
            assignments.add(assignment);
            return;
        }
        for (int i = start; i < colors.size(); i++) {
            swap(colors, start, i);
            permute(assignments, colors, start + 1);
            swap(colors, start, i);
        }
    }

    private static void swap(List<String> colors, int i, int j) {
        String temp = colors.get(i);
        colors.set(i, colors.get(j));
        colors.set(j, temp);
    }

    private static Map<String, Integer> findOptimalAssignment(List<Map<String, Integer>> assignments, int targetBuyIn, int numPlayers) {
        Map<String, Integer> optimalAssignment = null;
        int maxChips = 0;

        for (Map<String, Integer> assignment : assignments) {
            int totalChips = calculateMaxChips(assignment, targetBuyIn);
            if (totalChips > maxChips && hasEnoughChips(assignment, totalChips, numPlayers)) {
                maxChips = totalChips;
                optimalAssignment = assignment;
            }
        }

        return optimalAssignment;
    }

    private static int calculateMaxChips(Map<String, Integer> assignment, int targetBuyIn) {
        int[] dp = new int[targetBuyIn + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int amount = 1; amount <= targetBuyIn; amount++) {
            for (String color : assignment.keySet()) {
                int denomination = assignment.get(color);
                if (denomination <= amount && dp[amount - denomination] != Integer.MAX_VALUE) {
                    dp[amount] = Math.min(dp[amount], dp[amount - denomination] + 1);
                }
            }
        }

        return dp[targetBuyIn] == Integer.MAX_VALUE ? 0 : dp[targetBuyIn];
    }

    private static boolean hasEnoughChips(Map<String, Integer> assignment, int totalChips, int numPlayers) {
        for (String color : assignment.keySet()) {
            int requiredChips = totalChips * numPlayers;
            if (chipQuantities.get(color) < requiredChips) {
                return false;
            }
        }
        return true;
    }

    private static void distributeChips(Map<String, Integer> assignment, int targetBuyIn, int numPlayers) {
        int totalChips = calculateMaxChips(assignment, targetBuyIn);
        System.out.println("Chips per player: " + totalChips);

        for (String color : assignment.keySet()) {
            int chipsPerPlayer = totalChips;
            int totalRequired = chipsPerPlayer * numPlayers;
            System.out.println(color + " chips: " + chipsPerPlayer + " per player, " + totalRequired + " total");
        }
    }
}