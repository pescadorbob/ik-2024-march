package com.brent.ik.combinations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
public class LetterCombinationsOfAPhoneNumberShould {
    public static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of("23", asList("ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf")),
                Arguments.of("", emptyList()
                ));
    }

    @ParameterizedTest
    @MethodSource("input")
    @DisplayName("produce all possible letter combinations that the number could represent given digits 2 - 9")
    void produce_all_possible_letter_combinations_that_the_number_could_represent_given_digits_2_9(String digits, List<String> expected) {


        var actual = possibleLetterCombinations(digits);

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    Map<Integer, String> digitToLetterMap = Map.of(2, "abc", 3, "def", 4, "ghi", 5, "jkl", 6, "mno", 7, "pqrs", 8, "tuv", 9, "wxyz");

    private List<String> possibleLetterCombinations(String digits) {
        if(digits.isEmpty()) return emptyList();
        var letters = digitToLetterMap.get(parseInt(String.valueOf(digits.charAt(0)))).toCharArray();

        // base case
        if (digits.length() == 1) {
            var solution = new ArrayList<String>();
            for (char letter : letters) {
                solution.add(String.valueOf(letter));
            }
            return solution;
        }

        // iterate over the letters mapped to the first digit in the string, plus all the combos of subordinate workers
        var solution = new ArrayList<String>();
        var subCombos = possibleLetterCombinations(digits.substring(1));
        for (char letter : letters) {
            for (String subCombo : subCombos) {
                solution.add(letter + subCombo);
            }
        }
        return solution;
    }
}
