package com.brent.ik.claude.poker1a;

import java.util.HashMap;
import java.util.Map;

// Represents the available chips in the poker set
public class PokerSet {
    private final Map<String, Integer> chips;

    public PokerSet() {
        this.chips = new HashMap<>();
    }

    public void addChips(String color, int count) {
        chips.put(color, count);
    }

    public Map<String, Integer> getChips() {
        return new HashMap<>(chips);
    }

    public int getChipCount(String color) {
        return chips.getOrDefault(color, 0);
    }
}