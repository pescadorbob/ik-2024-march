package com.brent.ik.claude.poker1a;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class PokerChipDistributorTest {
    @Test
    public void testOneToOneMappingConstraint() {
        // Create poker set
        PokerSet pokerSet = new PokerSet();
        pokerSet.addChips("White", 149);
        pokerSet.addChips("Red", 143);
        pokerSet.addChips("Blue", 98);
        pokerSet.addChips("Green", 50);
        pokerSet.addChips("Black", 46);
        
        // Define possible denominations (same number as colors)
        List<Double> denominations = Arrays.asList(0.25, 0.50, 1.0, 2.0, 5.0);
        
        PokerChipDistributor distributor = new PokerChipDistributor();
        
        ChipDistribution result = distributor.findOptimalDistribution(
            pokerSet,
            denominations,
            31.0,
            10
        );
        
        assertNotNull(result);
        
        // Verify one-to-one mapping constraint
        Set<Double> usedDenominations = new HashSet<>();
        Set<String> usedColors = new HashSet<>();
        System.out.println("Color, Count, Denomination, Value");
        double total = 0;

        for (Map.Entry<Chip, Integer> entry : result.getDistribution().entrySet()) {
            Chip chip = entry.getKey();
            
            // Each denomination should be used only once
            assertTrue(
                usedDenominations.add(chip.getDenomination()),"Denomination already used: " + chip.getDenomination());
                
            // Each color should be used only once
            assertTrue(
                usedColors.add(chip.getColor()),"Color already used: " + chip.getColor());
            var value = entry.getKey().getDenomination()* entry.getValue();
            System.out.println(String.format("%s, %d, $%.2f, $%.2f",entry.getKey().getColor(),entry.getValue(),entry.getKey().getDenomination(), value));
            total += value;

        }
        
        // Number of unique denominations should equal number of colors
        assertEquals(pokerSet.getChips().size(), usedDenominations.size());
        assertEquals(pokerSet.getChips().size(), usedColors.size());

        System.out.println(String.format(",,, $%.2f", total));
        System.out.println("Distribution: " + result.getTotalChips());

    }
}