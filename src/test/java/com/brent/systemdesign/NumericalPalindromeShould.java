package com.brent.systemdesign;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


public class NumericalPalindromeShould {
    public static Stream<Arguments> determine_if_the_number_is_a_palindrome() {
        return Stream.of(
                Arguments.of(4994,true),
                Arguments.of(52025,true),
                Arguments.of(52,false),
                Arguments.of(-52025,false)
        );
    }

    @ParameterizedTest
    @MethodSource
    void determine_if_the_number_is_a_palindrome(int input, boolean expected){

        var actual = isPalindrome(input);

        assertThat(actual).isEqualTo(expected);
    }
    boolean isPalindrome(int input){
        var reverse = reverse(input);
        return reverse == input;
    }
    int reverse(int input){
        int reversed =  input % 10;

        int remaining = input / 10;
        while(remaining>0){
            var lastDigit = remaining % 10;
            reversed = reversed * 10;
            reversed += lastDigit;
            remaining = remaining /10;
        }
        return reversed;
    }
}
