package com.brent.ik.sort;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class IsSortedWithSpecificAlphabet {
    public static Stream<Arguments> shouldReportSorted_givenAlphabetAndSortedWords() {
        return Stream.of(
                Arguments.of(asList("cat", "bat", "tab"), "cbat", true),
                Arguments.of(asList("bat", "cat", "tab"), "cbat", false),
                Arguments.of(asList("catt", "ba", "t"), "cbat", true),
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
        var alphabetMap = mapAlphabet(alphabet);
        int length = words.get(0).length();
        for (int colI = 0; colI < length; colI++) {
            boolean isDifferent = true;
            for (int rowI = 0; rowI < words.size()-1; rowI++) {
                if (!isLexicographicallyBefore(words, colI, rowI, alphabetMap)) {
                    return false;
                }
                var index1 = alphabetMap.get(words.get(rowI).charAt(colI));
                var index2 = alphabetMap.get(words.get(rowI+1).charAt(colI));
                if(index1.equals(index2) ){
                    isDifferent = false;
                } else{
                    isDifferent &= true;
                }
            }
            if(isDifferent) {
                break;
            }
        }
        return true;
    }

    private boolean isLexicographicallyBefore(List<String> words, int colI, int rowI, Map<Character,Integer> alphabet) {
        if (words.get(rowI).length() - 1 >= colI &&
                words.get(rowI + 1).length() - 1 < colI) {
            return false;
        }
        var index1 = alphabet.get(words.get(rowI).charAt(colI));
        var index2 = alphabet.get(words.get(rowI+1).charAt(colI));
        return index1 <= index2;
    }
    private Map<Character, Integer> mapAlphabet(String alphabet) {
        var alphabetMap = new HashMap<Character,Integer>();
        for (int index = 0; index <alphabet.length(); index++) {
            alphabetMap.put(alphabet.charAt(index),index);
        }
        return alphabetMap;
    }
}
