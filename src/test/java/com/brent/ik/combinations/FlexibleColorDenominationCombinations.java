package com.brent.ik.combinations;
import java.util.ArrayList;
import java.util.List;


public class FlexibleColorDenominationCombinations {
    public static void main(String[] args) {
        List<String> colors = new ArrayList<>();
        colors.add("white");
        colors.add("black");
        colors.add("blue");

        List<Integer> denominations = new ArrayList<>();
        denominations.add(1);
        denominations.add(2);
        denominations.add(7);

        generateCombinations(colors, denominations, new ArrayList<String>(), 0, new boolean[denominations.size()]);
    }

    private static void generateCombinations(List<String> colors, List<Integer> denominations,
                                             List<String> currentCombination, int colorIndex, boolean[] usedDenominations) {
        if (colorIndex == colors.size()) {
            System.out.println(String.join(", ", currentCombination));
            return;
        }

        for (int i = 0; i < denominations.size(); i++) {
            if (!usedDenominations[i]) {
                usedDenominations[i] = true;
                List<String> newCombination = new ArrayList<>(currentCombination);
                newCombination.add(colors.get(colorIndex) + "-" + denominations.get(i));
                generateCombinations(colors, denominations, newCombination, colorIndex + 1, usedDenominations);
                usedDenominations[i] = false;
            }
        }
    }
}
