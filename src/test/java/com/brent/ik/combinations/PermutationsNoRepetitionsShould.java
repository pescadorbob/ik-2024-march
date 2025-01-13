package com.brent.ik.combinations;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class PermutationsNoRepetitionsShould {
    @Test
    void produceAllPermutationsWithoutRepetitions_givenPossibleValues() {
        var possibleValues = asList("A", "B","C");
        var expected = asList("",
                "A",
                "AB",
                "ABC",
                "AC",
                "ACB",
                "B",
                "BA",
                "BAC",
                "BC",
                "BCA",
                "C",
                "CA",
                "CAB",
                "CB",
                "CBA");

        var actual = permutationsWithoutRepetitions(possibleValues);

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    private List<String> permutationsWithoutRepetitions(List<String> possibleValues) {
        var results = new ArrayList<String>();

        pHelper(possibleValues, "", results, possibleValues.size());
        return results;
    }

    private void pHelper(List<String> possibleValues, String partialSolution, ArrayList<String> results, int n) {
        if (isPossibleSolution(n,possibleValues,partialSolution,results)) {
            results.add(partialSolution);
            if(!moreSolutionsExist(n,possibleValues,partialSolution,results)){
                return;
            }
        }
        possibleValues.forEach(letter -> {
            if(isAllowed(partialSolution,letter)){
                pHelper(possibleValues,partialSolution + letter,results,n-1);
            }
        });
    }

    private boolean moreSolutionsExist(int n, List<String> possibleValues, String partialSolution, ArrayList<String> results) {
        return n>0;
    }

    private boolean isPossibleSolution(int n, List<String> possibleValues, String partialSolution, ArrayList<String> results) {
        return !results.contains(partialSolution);
    }

    private boolean isAllowed(String partialSolution, String letter) {
        return !partialSolution.contains(letter);
    }
}
