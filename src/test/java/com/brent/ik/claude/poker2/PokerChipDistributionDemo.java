package com.brent.ik.claude.poker2;

import java.util.*;

class PokerChipDistributor {
    static class Chip {
        String color;
        int denomination; // in cents
        int available;

        Chip(String color, int denomination, int available) {
            this.color = color;
            this.denomination = denomination;
            this.available = available;
        }
    }

    static class Distribution {
        Map<String, Integer> chipCounts;
        int totalChips;
        int totalValue;

        Distribution() {
            this.chipCounts = new HashMap<>();
            this.totalChips = 0;
            this.totalValue = 0;
        }
    }

    private List<Chip> chips;
    private int targetAmount; // in cents
    private int numPlayers;

    public PokerChipDistributor(int targetAmount, int numPlayers) {
        this.targetAmount = targetAmount * 100; // convert to cents
        this.numPlayers = numPlayers;
        this.chips = new ArrayList<>();
    }

    public void addChip(String color, int denomination, int available) {
        chips.add(new Chip(color, denomination, available));
    }

    public Distribution calculateOptimalDistribution() {
        // Sort chips by denomination in descending order
        chips.sort((a, b) -> b.denomination - a.denomination);
        
        Distribution bestDistribution = new Distribution();
        Distribution currentDistribution = new Distribution();
        
        // Try to find the optimal distribution using backtracking
        findOptimalDistribution(this.targetAmount, 0, currentDistribution, bestDistribution);
        
        // Verify physical constraints
        adjustForPhysicalConstraints(bestDistribution);
        
        return bestDistribution;
    }

    private void findOptimalDistribution(int remainingAmount, int chipIndex, 
                                       Distribution current, Distribution best) {
        // Base cases
        if (remainingAmount == 0) {
            if (current.totalChips > best.totalChips) {
                copyDistribution(current, best);
            }
            return;
        }
        if (chipIndex >= chips.size() || remainingAmount < 0) {
            return;
        }

        Chip chip = chips.get(chipIndex);
        int maxChips = Math.min(remainingAmount / chip.denomination, 
                              chip.available / numPlayers);

        for (int i = maxChips; i >= 0; i--) {
            // Try using i chips of current denomination
            current.chipCounts.put(chip.color, i);
            current.totalChips += i;
            current.totalValue += i * chip.denomination;

            findOptimalDistribution(remainingAmount - (i * chip.denomination), 
                                  chipIndex + 1, current, best);

            // Backtrack
            current.chipCounts.put(chip.color, 0);
            current.totalChips -= i;
            current.totalValue -= i * chip.denomination;
        }
    }

    private void adjustForPhysicalConstraints(Distribution distribution) {
        boolean needsAdjustment;
        do {
            needsAdjustment = false;
            for (Chip chip : chips) {
                int count = distribution.chipCounts.getOrDefault(chip.color, 0);
                if (count * numPlayers > chip.available) {
                    needsAdjustment = true;
                    int newCount = chip.available / numPlayers;
                    int difference = (count - newCount) * chip.denomination;
                    distribution.chipCounts.put(chip.color, newCount);
                    distribution.totalChips -= (count - newCount);
                    // Redistribute the difference to other chips
                    redistributeValue(difference, distribution, chip.color);
                }
            }
        } while (needsAdjustment);
    }

    private void redistributeValue(int amount, Distribution distribution, String skipColor) {
        for (Chip chip : chips) {
            if (!chip.color.equals(skipColor)) {
                int additionalChips = Math.min(
                    amount / chip.denomination,
                    (chip.available / numPlayers) - distribution.chipCounts.getOrDefault(chip.color, 0)
                );
                if (additionalChips > 0) {
                    distribution.chipCounts.merge(chip.color, additionalChips, Integer::sum);
                    distribution.totalChips += additionalChips;
                    amount -= additionalChips * chip.denomination;
                }
                if (amount == 0) break;
            }
        }
    }

    private void copyDistribution(Distribution from, Distribution to) {
        to.chipCounts.clear();
        to.chipCounts.putAll(from.chipCounts);
        to.totalChips = from.totalChips;
        to.totalValue = from.totalValue;
    }
}

// Main class to demonstrate usage
public class PokerChipDistributionDemo {
    public static void main(String[] args) {
        // Example usage
        int buyIn = 31; // $31
        int numPlayers = 10;
        
        PokerChipDistributor distributor = new PokerChipDistributor(buyIn, numPlayers);
        
        // Add available chips (color, denomination in cents, available quantity)
        distributor.addChip("White", 25, 149);   // 25¢
        distributor.addChip("Red", 50, 143);     // 50¢
        distributor.addChip("Blue", 100, 98);    // $1
        distributor.addChip("Green", 200, 50);   // $2
        distributor.addChip("Black", 500, 46);   // $5

        PokerChipDistributor.Distribution result = distributor.calculateOptimalDistribution();

        // Print results
        System.out.println("Optimal Chip Distribution per Player:");
        System.out.println("------------------------------------");
        for (Map.Entry<String, Integer> entry : result.chipCounts.entrySet()) {
            String color = entry.getKey();
            int count = entry.getValue();
            System.out.printf("%s chips: %d%n", color, count);
        }
        System.out.printf("Total chips per player: %d%n", result.totalChips);
        System.out.printf("Total value: $%.2f%n", result.totalValue / 100.0);
    }
}