package com.brent.ik.combinations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Deleting the same characters from an array of strings at the same time should ")
public class DeletingTheSameCharactersFromAnArrayofStringsShould {
    public static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(asList("bb", "bb", "bb"),0),
                Arguments.of(asList("bzb", "byb", "bxb"),1),
                Arguments.of(asList("bzbz", "byby", "bxbx"),2),
                Arguments.of(asList("ca", "bb", "ac"),1),
                Arguments.of(asList("abcza", "abcyb", "abcxc"),1)
        );
    }

    @ParameterizedTest
    @MethodSource("input")
    @DisplayName("produce the minimum number of deletions required to make the array of strings appear in lexicographical order")
    void produceTheMinimumNumberOfDeletionsRequiredToMakeTheArrayOfStringsAppearInLexicographicOrder(List<String> input,int expected) {
        var actual = calculateMinimumNumberOfDeletionsRequiredForLexicographicOrder(input);

        assertThat(actual).isEqualTo(expected);
    }

    private Integer calculateMinimumNumberOfDeletionsRequiredForLexicographicOrder(List<String> input) {
        var bestSolution = new ArrayList<Integer>();
        bestSolution.add(Integer.MAX_VALUE);
        var originalSize = input.getFirst().length();

        lHelper(input, bestSolution, originalSize, 0);
        return bestSolution.getFirst();
    }

    private void lHelper(List<String> input, ArrayList<Integer> bestSolution, int originalSize, int index) {
        System.out.println("Checking input " + input.getFirst() + " " + input.get(1) + " " + input.get(2) + " index: " + index);
        if (isSorted(input)) {
            int deletions = originalSize - input.getFirst().length();
            System.out.println("Found solution " + input.getFirst() + " " + input.get(1) + " " + input.get(2) + " index: " + index + " deletions: " + deletions);
            var currSolution = bestSolution.getFirst();
            bestSolution.set(0, Math.min(currSolution, originalSize - input.getFirst().length()));
        }
        if (input.getFirst().isEmpty() || index >= input.getFirst().length()) return;
        lHelper(removeIndex(input, index), bestSolution, originalSize, index);
        if(index<input.getFirst().length()-1){
            lHelper(input, bestSolution, originalSize, index + 1);
        }

    }

    private List<String> removeIndex(List<String> input, int index) {
        var result = new ArrayList<String>();
        input.forEach(word -> {
            var prefix = word.substring(0, index);
            var suffix = "";
            if (index < word.length() - 1) {
                suffix = word.substring(index + 1);
            }
            result.add(prefix + suffix);
        });
        return result;
    }

    private boolean isSorted(List<String> input) {
        for (int i = 0; i < input.size() - 1; i++) {
            if (input.get(i).compareTo(input.get(i + 1)) > 0) {
                return false;
            }
        }
        return true;
    }

}
