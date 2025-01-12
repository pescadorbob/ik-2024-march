package com.brent.ik.combinations;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class BinaryStringPermutationsShould {
    @Test
    void produceAllBinaryStrings_givenALength(){
        var n = 2;
        var expected = asList("00","01","10","11");
        var actual = binaryStrings(n);
        assertThat(actual).isEqualTo(expected);
    }

    private List<String> binaryStrings(int n) {
        if(n==1){
            return asList("0","1");
        }
        var prev = binaryStrings(n-1);
        var result = new ArrayList<String>();
        for(String s : prev){
            result.add(s + "0");
            result.add(s + "1");

        }
        return result;

    }
}
