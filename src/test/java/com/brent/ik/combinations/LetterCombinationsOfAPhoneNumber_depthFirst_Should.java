package com.brent.ik.combinations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Letter combinations of a phone number should")
public class LetterCombinationsOfAPhoneNumber_depthFirst_Should {
    public static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of("23", asList("ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf")),
                Arguments.of("", emptyList()),
                Arguments.of("2", asList("a","b","c"))
        );
    }

    @ParameterizedTest
    @MethodSource("input")
    @DisplayName("produce all possible letter combinations that the number could represent given digits 2 - 9")
    void produce_all_possible_letter_combinations_that_the_number_could_represent_given_digits_2_9(String digits, List<String> expected) {


        var actual = letterCombinations(digits);

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    Map<Integer, String> digitToLetterMap = Map.of(2, "abc", 3, "def", 4, "ghi", 5, "jkl", 6, "mno", 7, "pqrs", 8, "tuv", 9, "wxyz");

    private List<String> letterCombinations(String digits) {
        var solution = new ArrayList<String>();
        if(digits.isEmpty()) return solution;
        helper(digits,"",solution);
        return solution;
    }
    private void helper(String digits, String currentCombination, List<String> solution){
        if(digits.isEmpty()) {
            solution.add(currentCombination);
            return;
        }
        var letters = getCharsMappedToFirstDigit(digits);


        // iterate over the letters mapped to the first digit in the string, plus all the combos of subordinate workers
        for (char letter : letters) {
            helper(digits.substring(1),currentCombination + letter,solution);
        }

    }

    private char[] getCharsMappedToFirstDigit(String digits) {
        return digitToLetterMap.get(parseInt(String.valueOf(digits.charAt(0)))).toCharArray();
    }
}
