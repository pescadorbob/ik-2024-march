package com.brent.ik.combinations;

import java.util.HashMap;
import java.util.Map;

public class PokerSet {
    Map<String,Integer> chips;
    public PokerSet(){
        chips = new HashMap<>();
    }
    public void addChips(String key, Integer integer) {
        chips.put(key,integer);
    }

    public Map<String, Integer> getChips() {
        return new HashMap<>(chips);
    }
    public void removeCoin(Coin coin){
        var currentValue = chips.get(coin.getColor());
        chips.put(coin.getColor(),currentValue-1);
    }

    @Override
    public String toString() {
        return "PokerSet{" +
                "chips=" + chips +
                '}';
    }
}
