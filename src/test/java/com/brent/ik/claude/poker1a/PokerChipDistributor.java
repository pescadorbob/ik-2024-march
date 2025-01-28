package com.brent.ik.claude.poker1a;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PokerChipDistributor {
    private static class DistributionState {
        int maxChips;
        int previousIndex;
        Chip usedChip;
        double balance;
        
        DistributionState() {
            this.maxChips = 0;
            this.previousIndex = -1;
            this.usedChip = null;
            this.balance = 0.0;
        }
    }
    
    public ChipDistribution findOptimalDistribution(
            PokerSet pokerSet,
            List<Double> possibleDenominations,
            double targetBuyIn,
            int numPlayers) {
        
        // Validate input
        if (pokerSet.getChips().size() != possibleDenominations.size()) {
            throw new IllegalArgumentException(
                "Number of chip colors must equal number of denominations");
        }
        
        // Generate all possible one-to-one mappings between colors and denominations
        List<List<Chip>> allAssignments = generatePermutations(
            new ArrayList<>(pokerSet.getChips().keySet()),
            possibleDenominations
        );
        
        ChipDistribution bestDistribution = null;
        
        // Try each possible assignment
        for (List<Chip> assignment : allAssignments) {
            ChipDistribution distribution = calculateDistribution(
                pokerSet, assignment, targetBuyIn, numPlayers);
                
            if (bestDistribution == null || 
                (distribution != null && 
                 distribution.getTotalChips() > bestDistribution.getTotalChips())) {
                bestDistribution = distribution;
            }
        }
        
        return bestDistribution;
    }
    
    // Generate all possible one-to-one mappings between colors and denominations
    private List<List<Chip>> generatePermutations(
            List<String> colors,
            List<Double> denominations) {
        List<List<Chip>> results = new ArrayList<>();
        boolean[] used = new boolean[denominations.size()];
        generatePermutationsHelper(colors, denominations, 0, used, new ArrayList<>(), results);
        return results;
    }
    
    private void generatePermutationsHelper(
            List<String> colors,
            List<Double> denominations,
            int colorIndex,
            boolean[] used,
            List<Chip> current,
            List<List<Chip>> results) {
        
        if (colorIndex == colors.size()) {
            results.add(new ArrayList<>(current));
            return;
        }
        
        String currentColor = colors.get(colorIndex);
        // Try each unused denomination for this color
        for (int i = 0; i < denominations.size(); i++) {
            if (!used[i]) {
                used[i] = true;
                current.add(new Chip(currentColor, denominations.get(i)));
                generatePermutationsHelper(colors, denominations, colorIndex + 1, used, current, results);
                current.remove(current.size() - 1);
                used[i] = false;
            }
        }
    }

    private ChipDistribution calculateDistribution(
            PokerSet pokerSet,
            List<Chip> chipAssignment,
            double targetBuyIn,
            int numPlayers) {

        // Find minimum denomination for scaling
        double minDenomination = chipAssignment.stream()
            .mapToDouble(Chip::getDenomination)
            .min()
            .orElse(0.25);

        // Scale target buy-in to work with integers
        int scaledTarget = (int)(targetBuyIn / minDenomination);

        // Initialize DP table
        DistributionState[] dp = new DistributionState[scaledTarget + 1];
        for (int i = 0; i <= scaledTarget; i++) {
            dp[i] = new DistributionState();
        }

        // Fill DP table
        for (int amount = 1; amount <= scaledTarget; amount++) {
            for (Chip chip : chipAssignment) {
                int scaledDenom = (int)(chip.getDenomination() / minDenomination);
                if (amount >= scaledDenom &&
                    hasEnoughChips(pokerSet, dp, amount - scaledDenom, chip, numPlayers)) {

                    int newChips = dp[amount - scaledDenom].maxChips + numPlayers;
                    if (newChips > dp[amount].maxChips) {
                        dp[amount].maxChips = newChips;
                        dp[amount].previousIndex = amount - scaledDenom;
                        dp[amount].usedChip = chip;
                        dp[amount].balance = chip.getDenomination() +
                            (amount - scaledDenom > 0 ? dp[amount - scaledDenom].balance : 0);
                    }
                }
            }
        }

        // Build solution
        if (Math.abs(dp[scaledTarget].balance - targetBuyIn) > 0.001) {
            return null; // No valid solution found
        }

        return buildSolution(dp, scaledTarget, chipAssignment, numPlayers);
    }

    private boolean hasEnoughChips(
            PokerSet pokerSet,
            DistributionState[] dp,
            int index,
            Chip chip,
            int numPlayers) {

        int chipsNeeded = countChipsInPath(dp, index, chip) * numPlayers + numPlayers;
        return pokerSet.getChipCount(chip.getColor()) >= chipsNeeded;
    }

    private int countChipsInPath(DistributionState[] dp, int index, Chip chip) {
        int count = 0;
        DistributionState current = dp[index];
        while (current.usedChip != null) {
            if (current.usedChip.equals(chip)) {
                count++;
            }
            current = dp[current.previousIndex];
        }
        return count;
    }

    private ChipDistribution buildSolution(
            DistributionState[] dp,
            int targetIndex,
            List<Chip> chipAssignment,
            int numPlayers) {

        Map<Chip, Integer> distribution = new HashMap<>();
        int totalChips = 0;
        double totalValue = 0.0;

        // Initialize distribution with zero counts
        chipAssignment.forEach(chip -> distribution.put(chip, 0));

        // Build distribution from DP table
        DistributionState current = dp[targetIndex];
        while (current.usedChip != null) {
            distribution.merge(current.usedChip, 1, Integer::sum);
            totalChips++;
            totalValue += current.usedChip.getDenomination();
            current = dp[current.previousIndex];
        }

        // Scale distribution by number of players
        distribution.replaceAll((chip, count) -> count * numPlayers);
        totalChips *= numPlayers;

        return new ChipDistribution(distribution, totalChips, totalValue);
    }
}