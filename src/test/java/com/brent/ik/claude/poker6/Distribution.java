package com.brent.ik.claude.poker6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<Distribution> bestDistributions;  // Changed to list of distributions
    private int maxChips;  // Track the maximum chips found
    private final double EPSILON = 0.01;

    public ChipDistributionSolver(double targetBuyIn, int numPlayers,
                                 Map<ChipColor, Integer> availableChips,
                                 double[] possibleDenominations) {
        this.targetBuyIn = targetBuyIn;
        this.numPlayers = numPlayers;
        this.availableChips = new HashMap<>(availableChips);
        this.possibleDenominations = possibleDenominations.clone();
        this.bestDistributions = new ArrayList<>();
        this.maxChips = 0;
    }

    public List<Distribution> solve() {
        List<ChipColor> colors = new ArrayList<>(availableChips.keySet());
        boolean[] used = new boolean[possibleDenominations.length];
        Map<ChipColor, Double> currentAssignment = new HashMap<>();
        
        generateDenominationAssignments(colors, 0, used, currentAssignment);
        
        return bestDistributions;
    }

    private void generateDenominationAssignments(List<ChipColor> colors, int colorIndex,
                                               boolean[] used,
                                               Map<ChipColor, Double> currentAssignment) {
        if (colorIndex == colors.size()) {
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
        List<ChipColor> colorList = new ArrayList<>(denominationAssignment.keySet());
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
            Distribution distribution = new Distribution(currentCounts, denominations);
            int totalChips = distribution.getTotalChips();
            
            if (totalChips > maxChips) {
                // Found a better solution, clear previous solutions
                maxChips = totalChips;
                bestDistributions.clear();
                bestDistributions.add(distribution);
            } else if (totalChips == maxChips) {
                // Found another solution with same number of chips
                bestDistributions.add(distribution);
            }
            return;
        }

        if (colorIndex >= colors.size() || remainingValue < -EPSILON) {
            return;
        }

        ChipColor currentColor = colors.get(colorIndex);
        double denom = denominations.get(currentColor);
        int maxPossibleChips = (int) Math.min(
            remainingValue / denom,
            availableChips.get(currentColor) / numPlayers
        );

        for (int chips = maxPossibleChips; chips >= 0; chips--) {
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

    public void printAllSolutions() {
        if (bestDistributions.isEmpty()) {
            System.out.println("No valid distributions found!");
            return;
        }

        System.out.printf("\nFound %d optimal distributions with %d chips per player:%n", 
                         bestDistributions.size(), maxChips);
        
        for (int i = 0; i < bestDistributions.size(); i++) {
            Distribution dist = bestDistributions.get(i);
            System.out.printf("%nSolution %d:%n", i + 1);
            System.out.println("Color\t Denomination \t# of chips\t Player Total");
            System.out.println("-".repeat(50));
            
            // Sort colors by denomination for consistent output
            List<Map.Entry<ChipColor, Double>> sortedEntries = new ArrayList<>(
                dist.getDenominations().entrySet());
            sortedEntries.sort(Map.Entry.comparingByValue());
            
            for (Map.Entry<ChipColor, Double> entry : sortedEntries) {
                ChipColor color = entry.getKey();
                double denom = entry.getValue();
                int count = dist.getChipCounts().get(color);
                double total = count * denom;
                System.out.printf("%s\t $%.2f \t%d\t $%.2f%n",
                                color, denom, count, total);
            }
            System.out.println("-".repeat(50));
            System.out.printf("Total\t\t%d\t $%.2f%n",
                            dist.getTotalChips(),
                            dist.getTotalValue());
        }
    }
}

class Main {
    public static void main(String[] args) {
        double targetBuyIn = 31.0;
        int numPlayers = 10;
        
        Map<ChipColor, Integer> availableChips = new HashMap<>();
        availableChips.put(ChipColor.WHITE, 149);
        availableChips.put(ChipColor.RED, 143);
        availableChips.put(ChipColor.BLUE, 98);
        availableChips.put(ChipColor.GREEN, 50);
        availableChips.put(ChipColor.BLACK, 46);
        
        double[] possibleDenominations = {0.25, 0.50, 1.0, 2.0, 5.0,10.0};
        
        ChipDistributionSolver solver = new ChipDistributionSolver(
            targetBuyIn, numPlayers, availableChips, possibleDenominations);
        
        solver.solve();
        solver.printAllSolutions();
    }
}