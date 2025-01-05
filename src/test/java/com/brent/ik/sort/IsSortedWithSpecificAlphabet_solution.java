package com.brent.ik.sort;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class IsSortedWithSpecificAlphabet_solution {
    public static Stream<Arguments> shouldReportSorted_givenAlphabetAndSortedWords() {
        return Stream.of(
                Arguments.of(asList("cat", "bat", "tab"), "cbat", true),
                Arguments.of(asList("bat", "cat", "tab"), "cbat", false),
                Arguments.of(asList("catt", "ba", "t"), "cbat", true),
                Arguments.of(asList("c", "ba", "tatt"), "cbat", true),
                Arguments.of(asList("c", "catt", "ca"), "cbat", false),
                Arguments.of(asList("t", "ba", "catt"), "cbat", false)
        );
    }

    @ParameterizedTest
    @MethodSource
    void shouldReportSorted_givenAlphabetAndSortedWords(List<String> words, String alphabet, boolean expected) {

        var actual = isSorted(words, alphabet);
        assertThat(actual).isEqualTo(expected);
    }

    private boolean isSorted(List<String> words, String alphabet) {
        var alphaD = new HashMap<Character, Integer>();
        for (int i = 0; i < alphabet.length(); i++) {
            alphaD.put(alphabet.charAt(i),i);
        }
        for (int wordI = 0; wordI < words.size()-1; wordI++) {
            var word1 = words.get(wordI);
            var word2 = words.get(wordI+1);
            if(!sorted(word1,word2,alphaD)){
                return false;
            }
        }
        return true;
    }

    private boolean sorted(String word1, String word2, HashMap<Character, Integer> alphaD) {
        for (int charI = 0; charI < min(word1.length(), word2.length()); charI++) {
            var charLocation1 = alphaD.get(word1.charAt(charI));
            var charLocation2 = alphaD.get(word2.charAt(charI));
            if(charLocation1>charLocation2){
                return false;
            }
            if(charLocation1<charLocation2){
                return true;
            }
        }
        // after checking the letters, if you get here, the letters are the same
        // e.g. hello, hell
        // the 'h' is the same
        // the 'e' is the same
        // the 'l' is the same
        // the 'l' is the same
        // the 'o' and the blank space are different.
        // if the first word is longer than the second => not sorted
        // otherwise, sorted



        return word1.length() > word2.length();

    }
}
