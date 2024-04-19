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
                Arguments.of(new String []{"baa","abcd","abca","cab","cad"}, "bdac")

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