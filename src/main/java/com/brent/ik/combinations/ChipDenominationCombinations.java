package com.brent.ik.combinations;


import java.util.ArrayList;
import java.util.List;

public class ChipDenominationCombinations {
    public static List<List<Coin>> chipValueCombinations(List<String> colors, List<Double> denominations) {
        var slate = new ArrayList<List<Coin>>();
        var partialSolution = new ArrayList<Coin>();
        helper(colors, 0, denominations, slate, partialSolution, new boolean[denominations.size()]);
        return slate;
    }

    private static void helper(List<String> colors, int colorIndex, List<Double> denominations,
                        List<List<Coin>> slate, List<Coin> partialSolution, boolean[] usedDenominations) {

        if (colorIndex == colors.size()) {
            slate.add(new ArrayList<>(partialSolution));
            return;
        }
        for (int i = 0; i < denominations.size(); i++) {
            if (!usedDenominations[i]) {
                usedDenominations[i] = true;
                partialSolution.add(new Coin(colors.get(colorIndex), denominations.get(i)));
                helper(colors, colorIndex + 1, denominations, slate, partialSolution, usedDenominations);
                partialSolution.remove(partialSolution.size() - 1);
                usedDenominations[i] = false;
            }
        }
    }


}
