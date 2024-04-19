package com.brent.ik.graphs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.brent.ik.graphs.AlienAlphabet.getAlienAlphabet;
import static org.assertj.core.api.Assertions.assertThat;

class AlienAlphabetTest {

    private static Stream<Arguments> provideInput() {
        return Stream.of(
                Arguments.of(new String []{"baa","abcd","abca","cab","cad"}, "bdac"),
                Arguments.of(new String []{"eeeeeeeeeee"}, "e"),
                Arguments.of(new String []{"e","e","e","e","e",}, "e"),
                Arguments.of(new String []{"z","s",}, "zs"),
                Arguments.of(new String []{"yyyyyy","wwwwwwwww",}, "yw"),
                Arguments.of(new String []{"x","d","g",}, "xdg"),
                Arguments.of(new String []{"tt","tttz","tttt",}, "zt"),
                Arguments.of(new String []{"caa","aaa","aab"}, "cab")

        );
    }
    @ParameterizedTest
    @MethodSource("provideInput")
    void shouldFindAlienAlphabet_givenSortedListOfAlienWords(String [] words,String expected){
        var actual = getAlienAlphabet(words);
        assertThat(actual).isEqualTo(expected);
    }

    /*
    "words": ["caa", "aaa", "aab"]

     */


            /*
            baa
|
abcd
   |
abca
|
cab
  |
cad

             */
}