package com.brent.ik.combinations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Letter combinations of a phone number")
public class LetterCombinationsOfAPhoneNumberShould {
    @Test
    @DisplayName("produce all possible letter combinations that the number could represent given digits 2 - 9")
    void produce_all_possible_letter_combinations_that_the_number_could_represent_given_digits_2_9(){
        var digits = "23";
        var expected = asList("ad","ae","af","bd","be","bf","cd","ce","cf");

        var actual = possibleLetterCombinations(digits);

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    Map<Integer,String> digitToLetterMap = Map.of(2,"abc",3,"def",4,"ghi",5,"jkl",6,"mno",7,"pqrs",8,"tuv",9,"wxyz");

    private List<String> possibleLetterCombinations(String digits) {
        var letters = digitToLetterMap.get(parseInt(String.valueOf(digits.charAt(0)))).toCharArray();

        // base case
        if(digits.length() == 1){
            var solution = new ArrayList<String>();
            for(char letter:letters){
                solution.add(String.valueOf(letter));
            }
            return solution;
        }

        // iterate over the letters mapped to the first digit in the string, plus all the combos of subordinate workers
        var solution = new ArrayList<String>();
        var subCombos = possibleLetterCombinations(digits.substring(1));
        for (char letter : letters) {
            for(String subCombo:subCombos){
                solution.add(letter + subCombo);
            }
        }
        return solution;
    }
}
