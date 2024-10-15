package com.brent.ik.autoboxing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Number object comparison")
public class NumberObjectComparisonTest {
    @Test
    @DisplayName("should result in false given two number objects of the same value and '==' operand")
    void testTwoNumberIntegers(){
        Integer int1 = 100000;
        Integer int2 = 100000;
        boolean isEqual = int1 == int2;
        assertThat(isEqual).isFalse();
        // notice how two number objects compared using '==' results in them not being equal? You have to use equals
    }
    @Test
    @DisplayName("should result in true given two number objects of the same value and 'equals' method")
    void testTwoNumberIntegersWithEquals(){
        Integer int1 = 100000;
        Integer int2 = 100000;
        boolean isEqual = int1.equals(int2);
        assertThat(isEqual).isTrue();
        // notice how two number objects compared using 'equals' results in them being equal?
    }
}
