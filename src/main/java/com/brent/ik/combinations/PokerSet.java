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
}
