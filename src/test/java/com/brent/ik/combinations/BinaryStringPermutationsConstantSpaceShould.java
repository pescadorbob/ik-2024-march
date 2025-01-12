package com.brent.ik.combinations;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class BinaryStringPermutationsConstantSpaceShould {
    @Test
    void produceAllBinaryStrings_givenALength(){
        var n = 2;
        var expected = asList("00","01","10","11");
        var actual = binaryStrings(n);
        assertThat(actual).isEqualTo(expected);
    }

    private List<String> binaryStrings(int n) {

        var results = new ArrayList<String>();
        bHelper("",results,n);
        return results;
    }

    private void bHelper(String part, ArrayList<String> results, int n) {
        if(n==0) {
            results.add(part);
            return;
        }
        bHelper(part + "0",results,n-1);
        bHelper(part + "1",results,n-1);
    }
}
