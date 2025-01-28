package com.brent.ik.dp.poker;

import com.brent.ik.combinations.Coin;
import com.brent.ik.combinations.PokerSetSolution;

import java.util.HashMap;
import java.util.Map;

public class PokerSetSolutionTestBuilder {
    int totalchips;
    Map<Coin,Integer> coinAmount;

    public PokerSetSolutionTestBuilder() {
        coinAmount = new HashMap<>();
    }
    public static PokerSetSolutionTestBuilder aPokerSetSolution(){
        return new PokerSetSolutionTestBuilder();
    }
    public PokerSetSolutionTestBuilder withTotal(int total){
        this.totalchips = total;
        return this;
    }
    public PokerSetSolutionTestBuilder withCoinAmount(Coin coin, int amount){
        coinAmount.put(coin,amount);
        return this;
    }
    public PokerSetSolution build(){
        return new PokerSetSolution(coinAmount,totalchips);
    }
}
