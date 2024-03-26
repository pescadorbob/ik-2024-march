package com.brent.ik.merge;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class MagicThreeTest {

    @ParameterizedTest
    @MethodSource("provideArrays")
    void test_merge_one_into_another(ArrayList<Integer> first, ArrayList<String> expected){
        var actual = find_zero_sum(first);
        assertThat(actual).containsOnlyElementsOf(expected);
    }
    private static Stream<Arguments> provideArrays() {
        return Stream.of(
                Arguments.of(list(0,0,0),lists("0,0,0")),
                Arguments.of(list(0,0,0,0,0),lists("0,0,0")),
                Arguments.of(list(12, 34, -46),lists("-46,12,34")),
                Arguments.of(list(-600, 1000, -400, 239, -29),lists("-600,-400,1000")),
                Arguments.of(list(4, -2, -2, -1, -3),lists("-2,-2,4", "-3,-1,4"))
        );
    }

    private static ArrayList<String> lists(String... array) {
        return new ArrayList<>(Arrays.stream(array).toList());
    }

    private static ArrayList<Integer> list(Integer... array) {
        return new ArrayList<>(java.util.Arrays.stream(array).toList());
    }

    static ArrayList<String> find_zero_sum(ArrayList<Integer> arr) {
        Collections.sort(arr);
        var magicTriplets = new HashSet<String>();
        for(int i=0;i<arr.size()-1;i++){
            int first = arr.get(i);
            var sums = twoSums(arr,i+1,arr.size()-1,-first);
            var triplets = new ArrayList<String>();
            for(String sum : sums){
                triplets.add(String.format("%d,%s",arr.get(i),sum));
            }
            magicTriplets.addAll(triplets);
        }
        return new ArrayList<String>(magicTriplets.stream().toList());
    }

    static ArrayList<String> twoSums(ArrayList<Integer> arr, int start, int end, int lookFor){
        var sums = new ArrayList<String>();
        int l = start;
        int r = end;
        while(l<r && l<end && r>start){
            if(arr.get(l)+arr.get(r) == lookFor){
                sums.add(String.format("%d,%d",arr.get(l),arr.get(r)));
            }
            if(arr.get(l)+arr.get(r) >= lookFor){
                r--;
            } else {
                l++;
            }
        }

        return sums;

    }
}

