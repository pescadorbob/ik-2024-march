package com.brent.ik.claude.poker1;

import java.util.Objects;

// Represents a chip with its color and assigned denomination
public class Chip {
    private final String color;
    private final double denomination;

    public Chip(String color, double denomination) {
        this.color = color;
        this.denomination = denomination;
    }

    public String getColor() { return color; }
    public double getDenomination() { return denomination; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chip)) return false;
        Chip chip = (Chip) o;
        return Double.compare(chip.denomination, denomination) == 0 &&
                Objects.equals(color, chip.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, denomination);
    }
}