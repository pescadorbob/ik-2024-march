package com.brent.ik.recursive;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class RomanToIntegerShould {
    static final Map<String, Integer> additionMap = Map.of("I", 1, "V", 5, "X", 10, "L", 50, "C", 100, "D", 500, "M", 1000);
    static final Map<String, Integer> subtractionMap = Map.of("IV", 4, "IX", 9, "XL", 40, "XC", 90, "CD", 400, "CM", 900);

    public static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of("XIV",14),
                Arguments.of("XIV",14),
                Arguments.of("MCMXCIV",1994)
        );
    }

    @ParameterizedTest
    @MethodSource("input")
    void produce_the_integer_given_the_roman_numeral(String letters,int expected) {


        var actual = romanToInteger(letters);

        assertThat(actual).isEqualTo(expected);
    }

    private int romanToInteger(String letters) {
        if (letters.isEmpty()) {
            return 0;
        }
        if (letters.length() > 1) {
            var twoLetterValue = letters.substring(0, 2);
            if(subtractionMap.containsKey(twoLetterValue)){
                var value = subtractionMap.get(twoLetterValue);
                return value + romanToInteger(letters.substring(2));
            }
        }
        var value = additionMap.get(letters.substring(0,1));
        return value + romanToInteger(letters.substring(1));
    }
}
