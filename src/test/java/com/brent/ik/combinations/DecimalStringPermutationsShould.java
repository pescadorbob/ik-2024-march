package com.brent.ik.combinations;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class DecimalStringPermutationsShould {
    @Test
    void printAllCombinationsZeroToNine_givenLength() {
        var n = 2;
        var expected = asList(
                "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
                "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
                "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
                "50", "51", "52", "53", "54", "55", "56", "57", "58", "59",
                "60", "61", "62", "63", "64", "65", "66", "67", "68", "69",
                "70", "71", "72", "73", "74", "75", "76", "77", "78", "79",
                "80", "81", "82", "83", "84", "85", "86", "87", "88", "89",
                "90", "91", "92", "93", "94", "95", "96", "97", "98", "99"
        );
        var actual = decimalStringPermutations(n);
        assertThat(actual).isEqualTo(expected);
    }

    private List<String> decimalStringPermutations(int n) {
        if(n==1){
            return IntStream.range(0,10).mapToObj(Integer::toString).toList();
        }
        var prevSolutions = decimalStringPermutations(n-1);
        var partialSolutions = new ArrayList<String>();
        prevSolutions.forEach(prev->{
            IntStream.range(0,10).forEach(i->{
                partialSolutions.add(prev + i);
            });
        });
        return partialSolutions;
    }
}
