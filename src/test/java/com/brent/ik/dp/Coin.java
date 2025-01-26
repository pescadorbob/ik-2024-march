package com.brent.ik.dp;

import java.util.Objects;

public class Coin {
    private final String color;
    private final double denomination;

    public Coin(String color, double denomination) {
        this.color = color;
        this.denomination = denomination;
    }

    @Override
    public String toString() {
        return color + "-" + denomination;
    }

    public double getDenomination() {
        return denomination;
    }

    public String getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Coin coin = (Coin) o;
        return Double.compare(denomination, coin.denomination) == 0 && Objects.equals(color, coin.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, denomination);
    }
}
