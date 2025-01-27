package com.brent.ik.combinations;

import java.util.Map;

public class PokerSetSolution {
    private final Map<Coin, Integer> coinAmount;
    private final int totalchips;

    public PokerSetSolution(Map<Coin, Integer> coinAmount, int totalchips) {
        this.coinAmount = coinAmount;
        this.totalchips = totalchips;
    }
}
