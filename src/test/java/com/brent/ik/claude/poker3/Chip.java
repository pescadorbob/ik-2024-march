package com.brent.ik.claude.poker3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Represents a chip with its color and assigned denomination
class Chip {
    private final ChipColor color;
    private final double denomination;
    private final int availableCount;

    public Chip(ChipColor color, double denomination, int availableCount) {
        this.color = color;
        this.denomination = denomination;
        this.availableCount = availableCount;
    }

    public ChipColor getColor() { return color; }
    public double getDenomination() { return denomination; }
    public int getAvailableCount() { return availableCount; }
}

// Enum for chip colors
enum ChipColor {
    WHITE, RED, BLUE, GREEN, BLACK
}

// Class to represent a complete distribution solution
class Distribution {
    private final Map<ChipColor, Integer> chipCounts;
    private final Map<ChipColor, Double> denominations;
    private final int totalChips;
    private final double totalValue;

    public Distribution(Map<ChipColor, Integer> chipCounts, 
                       Map<ChipColor, Double> denominations) {
        this.chipCounts = chipCounts;
        this.denominations = denominations;
        this.totalChips = chipCounts.values().stream().mapToInt(Integer::intValue).sum();
        this.totalValue = calculateTotalValue();
    }

    private double calculateTotalValue() {
        double total = 0;
        for (Map.Entry<ChipColor, Integer> entry : chipCounts.entrySet()) {
            total += entry.getValue() * denominations.get(entry.getKey());
        }
        return total;
    }

    public int getTotalChips() { return totalChips; }
    public double getTotalValue() { return totalValue; }
    public Map<ChipColor, Integer> getChipCounts() { return chipCounts; }
    public Map<ChipColor, Double> getDenominations() { return denominations; }
}

class ChipDistributionSolver {
    private final double targetBuyIn;
    private final int numPlayers;
    private final Map<ChipColor, Integer> availableChips;
    private final double[] possibleDenominations;
    private Distribution bestDistribution;

    public ChipDistributionSolver(double targetBuyIn, int numPlayers,
                                 Map<ChipColor, Integer> availableChips,
                                 double[] possibleDenominations) {
        this.targetBuyIn = targetBuyIn;
        this.numPlayers = numPlayers;
        this.availableChips = availableChips;
        this.possibleDenominations = possibleDenominations;
        this.bestDistribution = null;
    }

    public Distribution solve() {
        // Generate all possible denomination assignments
        List<ChipColor> colors = new ArrayList<>(availableChips.keySet());
        boolean[] used = new boolean[possibleDenominations.length];
        Map<ChipColor, Double> currentAssignment = new HashMap<>();
        
        generateDenominationAssignments(colors, 0, used, currentAssignment);
        
        return bestDistribution;
    }

    private void generateDenominationAssignments(List<ChipColor> colors, int colorIndex,
                                               boolean[] used,
                                               Map<ChipColor, Double> currentAssignment) {
        if (colorIndex == colors.size()) {
            // We have a complete assignment, calculate distribution
            calculateDistribution(currentAssignment);
            return;
        }

        ChipColor currentColor = colors.get(colorIndex);
        for (int i = 0; i < possibleDenominations.length; i++) {
            if (!used[i]) {
                used[i] = true;
                currentAssignment.put(currentColor, possibleDenominations[i]);
                generateDenominationAssignments(colors, colorIndex + 1, used, currentAssignment);
                currentAssignment.remove(currentColor);
                used[i] = false;
            }
        }
    }

    private void calculateDistribution(Map<ChipColor, Double> denominationAssignment) {
        // Use linear programming approach to find optimal distribution
        Map<ChipColor, Integer> chipCounts = new HashMap<>();
        double remainingValue = targetBuyIn;
        List<ChipColor> sortedColors = new ArrayList<>(denominationAssignment.keySet());
        
        // Sort colors by denomination value (highest to lowest)
        sortedColors.sort((c1, c2) -> 
            Double.compare(denominationAssignment.get(c2), denominationAssignment.get(c1)));

        // Calculate how many chips of each denomination we need
        for (ChipColor color : sortedColors) {
            double denom = denominationAssignment.get(color);
            int maxChips = (int) (remainingValue / denom);
            int availablePerPlayer = availableChips.get(color) / numPlayers;
            int chips = Math.min(maxChips, availablePerPlayer);
            
            if (chips > 0) {
                chipCounts.put(color, chips);
                remainingValue -= chips * denom;
            } else {
                chipCounts.put(color, 0);
            }
        }

        // Check if this is a valid distribution
        if (Math.abs(remainingValue) < 0.01) {
            Distribution distribution = new Distribution(chipCounts, denominationAssignment);
            if (bestDistribution == null || 
                distribution.getTotalChips() > bestDistribution.getTotalChips()) {
                bestDistribution = distribution;
            }
        }
    }

    public void printSolution() {
        if (bestDistribution == null) {
            System.out.println("No valid distribution found!");
            return;
        }

        System.out.println("Optimal Distribution Found:");
        System.out.println("Total chips per player: " + bestDistribution.getTotalChips());
        System.out.println("Total value: $" + bestDistribution.getTotalValue());
        System.out.println("\nChip Distribution:");
        
        for (Map.Entry<ChipColor, Integer> entry : bestDistribution.getChipCounts().entrySet()) {
            ChipColor color = entry.getKey();
            int count = entry.getValue();
            double denom = bestDistribution.getDenominations().get(color);
            System.out.printf("%s: %d chips @ $%.2f = $%.2f%n",
                            color, count, denom, count * denom);
        }
    }
}

// Main class to demonstrate usage
class Main {
    public static void main(String[] args) {
        // Example usage
        double targetBuyIn = 31.0;
        int numPlayers = 10;
        
        Map<ChipColor, Integer> availableChips = new HashMap<>();
        availableChips.put(ChipColor.WHITE, 149);
        availableChips.put(ChipColor.RED, 143);
        availableChips.put(ChipColor.BLUE, 98);
        availableChips.put(ChipColor.GREEN, 50);
        availableChips.put(ChipColor.BLACK, 46);
        
        double[] possibleDenominations = {0.25, 0.50, 1.0, 2.0, 5.0, 10.0};
        
        ChipDistributionSolver solver = new ChipDistributionSolver(
            targetBuyIn, numPlayers, availableChips, possibleDenominations);
        
        Distribution solution = solver.solve();
        solver.printSolution();
    }
}