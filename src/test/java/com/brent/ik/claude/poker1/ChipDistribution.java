package com.brent.ik.claude.poker1;

import java.util.HashMap;
import java.util.Map;

// Represents the solution for chip distribution
public class ChipDistribution {
    private final Map<Chip, Integer> distribution;
    private final int totalChips;
    private final double totalValue;
    
    public ChipDistribution(Map<Chip, Integer> distribution, int totalChips, double totalValue) {
        this.distribution = new HashMap<>(distribution);
        this.totalChips = totalChips;
        this.totalValue = totalValue;
    }
    
    public Map<Chip, Integer> getDistribution() { return distribution; }
    public int getTotalChips() { return totalChips; }
    public double getTotalValue() { return totalValue; }
}