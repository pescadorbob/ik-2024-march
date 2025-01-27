package com.brent.ik.combinations;

import java.util.HashMap;
import java.util.Map;

public class PokerSetSolution {
    private Map<Coin, Integer> coinAmount;
    private int totalchips;

    public PokerSetSolution() {
        this.coinAmount = new HashMap<>();
    }

    public PokerSetSolution(Map<Coin, Integer> coinAmount, int totalchips) {
        this.coinAmount = coinAmount;
        this.totalchips = totalchips;
    }

    public int getTotalchips() {
        return totalchips;
    }

    public void setTotalchips(int totalchips) {
        this.totalchips = totalchips;
    }

    public Map<Coin, Integer> getCoinAmount() {
        return coinAmount;
    }

    public void setCoinAmount(Map<Coin, Integer> coinAmount) {
        this.coinAmount = coinAmount;
    }
}
