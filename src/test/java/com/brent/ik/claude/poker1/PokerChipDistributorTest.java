package com.brent.ik.claude.poker1;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PokerChipDistributorTest {
    @Test
    public void testOptimalDistribution() {
        // Create poker set
        PokerSet pokerSet = new PokerSet();
        pokerSet.addChips("White", 149);
        pokerSet.addChips("Red", 143);
        pokerSet.addChips("Blue", 98);
        pokerSet.addChips("Black", 46);
        pokerSet.addChips("Green", 50);
        
        // Define possible denominations
        List<Double> denominations = Arrays.asList(0.25, 0.50, 1.0, 2.0, 5.0, 10.0);
        
        // Create distributor
        PokerChipDistributor distributor = new PokerChipDistributor();
        
        // Find optimal distribution
        ChipDistribution result = distributor.findOptimalDistribution(
            pokerSet,
            denominations,
            31.0,  // target buy-in
            10     // number of players
        );
        
        // Verify result
        assertNotNull(result);
        assertEquals(31.0, result.getTotalValue(), 0.001);
        
        // Verify each chip count is within available limits
        System.out.println("Color, Count, Denomination, Value");
        double total = 0;
        for (Map.Entry<Chip, Integer> entry : result.getDistribution().entrySet()) {
            assertTrue(entry.getValue() <= pokerSet.getChipCount(entry.getKey().getColor()));
            var value = entry.getKey().getDenomination()* entry.getValue();
            System.out.println(String.format("%s, %d, $%.2f, $%.2f",entry.getKey().getColor(),entry.getValue(),entry.getKey().getDenomination(), value));
            total += value;
        }
        System.out.println(String.format(",,, $%.2f", total));
        System.out.println("Distribution: " + result.getTotalChips());
    }
}