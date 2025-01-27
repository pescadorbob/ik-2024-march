package com.brent.ik.dp;

import com.brent.ik.combinations.PokerSet;

import java.util.HashMap;
import java.util.Map;

public class PokerSetTestBuilder {
    Map<String,Integer> colorQuantityMap;

    public PokerSetTestBuilder(){
        colorQuantityMap = new HashMap<>();
    }
    public static PokerSetTestBuilder aPokerSet(){
        return new PokerSetTestBuilder();
    }
    public PokerSetTestBuilder withChips(String color,int quantity){
        colorQuantityMap.put(color,quantity);
        return this;
    }
    public PokerSet build(){
        var pokerSet = new PokerSet();
        for (String key:colorQuantityMap.keySet()){
            pokerSet.addChips(key,colorQuantityMap.get(key));
        }
        return pokerSet;
    }
}
