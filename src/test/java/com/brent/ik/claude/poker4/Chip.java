package com.brent.ik.claude.poker4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

enum ChipColor {
    WHITE, RED, BLUE, GREEN, BLACK
}

class Distribution {
    private final Map<ChipColor, Integer> chipCounts;
    private final Map<ChipColor, Double> denominations;
    private final int totalChips;
    private final double totalValue;

    public Distribution(Map<ChipColor, Integer> chipCounts, 
                       Map<ChipColor, Double> denominations) {
        this.chipCounts = new HashMap<>(chipCounts);
        this.denominations = new HashMap<>(denominations);
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
    public Map<ChipColor, Integer> getChipCounts() { return new HashMap<>(chipCounts); }
    public Map<ChipColor, Double> getDenominations() { return new HashMap<>(denominations); }
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
        this.availableChips = new HashMap<>(availableChips);
        this.possibleDenominations = possibleDenominations.clone();
        this.bestDistribution = null;
    }

    public Distribution solve() {
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
            Distribution dist = calculateDistribution(currentAssignment);
            if (dist != null && (bestDistribution == null || 
                dist.getTotalChips() > bestDistribution.getTotalChips())) {
                bestDistribution = dist;
            }
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

    private Distribution calculateDistribution(Map<ChipColor, Double> denominationAssignment) {
        Map<ChipColor, Integer> chipCounts = new HashMap<>();
        double remainingValue = targetBuyIn;
        List<ChipColor> sortedColors = new ArrayList<>(denominationAssignment.keySet());
        
        // Sort colors by denomination value (lowest to highest)
        sortedColors.sort((c1, c2) -> 
            Double.compare(denominationAssignment.get(c1), denominationAssignment.get(c2)));

        // First pass: Calculate minimum chips needed for each denomination
        for (ChipColor color : sortedColors) {
            double denom = denominationAssignment.get(color);
            // Initialize with 0 chips
            chipCounts.put(color, 0);
        }

        // Second pass: Start with smallest denominations and maximize chips
        for (ChipColor color : sortedColors) {
            double denom = denominationAssignment.get(color);
            int availablePerPlayer = availableChips.get(color) / numPlayers;
            
            // Calculate how many chips we can use while staying under target
            int maxChips = (int) (remainingValue / denom);
            int chips = Math.min(maxChips, availablePerPlayer);
            
            if (chips > 0) {
                chipCounts.put(color, chips);
                remainingValue -= chips * denom;
            }
        }

        // Verify the distribution is valid
        double totalValue = 0;
        for (Map.Entry<ChipColor, Integer> entry : chipCounts.entrySet()) {
            totalValue += entry.getValue() * denominationAssignment.get(entry.getKey());
        }

        // Check if this is a valid distribution (within 1 cent of target)
        if (Math.abs(totalValue - targetBuyIn) <= 0.01) {
            return new Distribution(chipCounts, denominationAssignment);
        }
        return null;
    }

    public void printSolution() {
        if (bestDistribution == null) {
            System.out.println("No valid distribution found!");
            return;
        }

        System.out.println("\nOptimal Distribution Found:");
        System.out.printf("Total chips per player: %d%n", bestDistribution.getTotalChips());
        System.out.printf("Total value: $%.2f%n", bestDistribution.getTotalValue());
        System.out.println("\nChip Distribution:");
        
        // Sort by denomination for cleaner output
        List<Map.Entry<ChipColor, Double>> sortedDenoms = new ArrayList<>(
            bestDistribution.getDenominations().entrySet());
        sortedDenoms.sort(Map.Entry.comparingByValue());
        
        for (Map.Entry<ChipColor, Double> entry : sortedDenoms) {
            ChipColor color = entry.getKey();
            double denom = entry.getValue();
            int count = bestDistribution.getChipCounts().get(color);
            System.out.printf("%s: %d chips @ $%.2f = $%.2f%n",
                            color, count, denom, count * denom);
        }
    }
}

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
        
        double[] possibleDenominations = {0.25, 0.50, 1.0, 2.0, 5.0};
        
        ChipDistributionSolver solver = new ChipDistributionSolver(
            targetBuyIn, numPlayers, availableChips, possibleDenominations);
        
        Distribution solution = solver.solve();
        solver.printSolution();
    }
}