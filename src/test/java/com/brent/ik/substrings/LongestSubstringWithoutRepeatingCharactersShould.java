package com.brent.ik.substrings;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class LongestSubstringWithoutRepeatingCharactersShould {
    public static Stream<Arguments> characters() {
        return Stream.of(
                Arguments.of("abcabcbb",3),
                Arguments.of("bbb",1),
                Arguments.of(" ",1),
                Arguments.of("dvdf",3),
                Arguments.of("pwwkew",3)
        );
    }

    /*
     O(n2)
     */
    @ParameterizedTest
    @MethodSource("characters")
    void produce_the_length_of_the_longest_substring_give_characters(String characters,int expected){

        var actual = lengthOfLongestSubstring(characters);

        assertThat(actual).isEqualTo(expected);
    }

    public int lengthOfLongestSubstring(String characters) {
        int longest = 0;
        int l = 0;
        var longestString = new HashSet<Character>();
        for (int right = 0; right < characters.length(); right++) {
            while(longestString.contains(characters.charAt(right))){
                longestString.remove(characters.charAt(l));
                l++;
            }
            longestString.add(characters.charAt(right));
            longest = Math.max(longest,right-l+1);
        }
        return longest;
    }
}
