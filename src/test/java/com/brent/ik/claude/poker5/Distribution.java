package com.brent.ik.claude.poker5;

import java.util.*;

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
    private final double EPSILON = 0.01;  // For floating point comparisons

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
        
        // Try all possible denomination assignments
        generateDenominationAssignments(colors, 0, used, currentAssignment);
        
        return bestDistribution;
    }

    private void generateDenominationAssignments(List<ChipColor> colors, int colorIndex,
                                               boolean[] used,
                                               Map<ChipColor, Double> currentAssignment) {
        if (colorIndex == colors.size()) {
            // Try to find optimal chip distribution for this denomination assignment
            findOptimalDistribution(currentAssignment);
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

    private void findOptimalDistribution(Map<ChipColor, Double> denominationAssignment) {
        // Create arrays for dynamic programming
        List<ChipColor> colorList = new ArrayList<>(denominationAssignment.keySet());
        int n = colorList.size();
        
        // Try all possible combinations of chip counts
        Map<ChipColor, Integer> currentCounts = new HashMap<>();
        for (ChipColor color : colorList) {
            currentCounts.put(color, 0);
        }

        findOptimalRecursive(colorList, 0, denominationAssignment, currentCounts, targetBuyIn);
    }

    private void findOptimalRecursive(List<ChipColor> colors, int colorIndex,
                                    Map<ChipColor, Double> denominations,
                                    Map<ChipColor, Integer> currentCounts,
                                    double remainingValue) {
        if (Math.abs(remainingValue) < EPSILON) {
            // Valid solution found
            Distribution distribution = new Distribution(currentCounts, denominations);
            if (bestDistribution == null || distribution.getTotalChips() > bestDistribution.getTotalChips()) {
                bestDistribution = distribution;
            }
            return;
        }

        if (colorIndex >= colors.size() || remainingValue < -EPSILON) {
            return;
        }

        ChipColor currentColor = colors.get(colorIndex);
        double denom = denominations.get(currentColor);
        int maxChips = (int) Math.min(
            remainingValue / denom,
            availableChips.get(currentColor) / numPlayers
        );

        // Try different numbers of chips for this color
        for (int chips = maxChips; chips >= 0; chips--) {
            currentCounts.put(currentColor, chips);
            findOptimalRecursive(
                colors,
                colorIndex + 1,
                denominations,
                currentCounts,
                remainingValue - (chips * denom)
            );
        }
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
        
        // Print in a formatted table
        System.out.println("Color\t Denomination \t# of chips\t Player Total");
        for (ChipColor color : bestDistribution.getDenominations().keySet()) {
            double denom = bestDistribution.getDenominations().get(color);
            int count = bestDistribution.getChipCounts().get(color);
            double total = count * denom;
            System.out.printf("%s\t $%.2f \t%d\t $%.2f%n",
                            color, denom, count, total);
        }
        System.out.printf("Total\t\t%d\t $%.2f%n",
                         bestDistribution.getTotalChips(),
                         bestDistribution.getTotalValue());
    }
}

class Main {
    public static void main(String[] args) {
        // Test case
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