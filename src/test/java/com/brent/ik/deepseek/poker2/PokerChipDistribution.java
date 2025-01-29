package com.brent.ik.deepseek.poker2;

import java.util.*;

public class PokerChipDistribution {

    public static void main(String[] args) {
        int targetBuyIn = 3100; // $31 in cents
        int numPlayers = 10;
        Map<String, Integer> availableChips = new HashMap<>();
        availableChips.put("White", 149);
        availableChips.put("Red", 143);
        availableChips.put("Blue", 98);
        availableChips.put("Green", 50);
        availableChips.put("Black", 46);
        List<Integer> denominations = Arrays.asList(25, 50, 100, 200, 500, 1000); // in cents

        Map<String, Integer> bestAssignment = new HashMap<>();
        int maxChipsPerPlayer = 0;

        // Generate all possible assignments of denominations to chip colors
        List<List<Integer>> allCombinations = generateCombinations(denominations, availableChips.size());

        for (List<Integer> combination : allCombinations) {
            Map<String, Integer> assignment = new HashMap<>();
            int index = 0;
            for (String color : availableChips.keySet()) {
                assignment.put(color, combination.get(index++));
            }

            int chipsPerPlayer = calculateMaxChipsPerPlayer(targetBuyIn, assignment, availableChips, numPlayers);
            if (chipsPerPlayer > maxChipsPerPlayer) {
                maxChipsPerPlayer = chipsPerPlayer;
                bestAssignment = assignment;
            }
        }

        System.out.println("Best Assignment: " + bestAssignment);
        System.out.println("Max Chips Per Player: " + maxChipsPerPlayer);
    }

    private static List<List<Integer>> generateCombinations(List<Integer> denominations, int size) {
        List<List<Integer>> result = new ArrayList<>();
        generateCombinationsHelper(denominations, size, new ArrayList<>(), result);
        return result;
    }

    private static void generateCombinationsHelper(List<Integer> denominations, int size, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == size) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = 0; i < denominations.size(); i++) {
            current.add(denominations.get(i));
            generateCombinationsHelper(denominations, size, current, result);
            current.remove(current.size() - 1);
        }
    }

    private static int calculateMaxChipsPerPlayer(int targetBuyIn, Map<String, Integer> assignment, Map<String, Integer> availableChips, int numPlayers) {
        int totalChips = 0;
        for (String color : assignment.keySet()) {
            int denomination = assignment.get(color);
            int available = availableChips.get(color);
            totalChips += available / numPlayers * denomination;
        }
        if (totalChips < targetBuyIn) {
            return 0;
        }
        return totalChips / targetBuyIn;
    }
}