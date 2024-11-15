package com.brent.ik.merge;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class MergeSortedArray {

    private static Stream<Arguments> provideArrays() {
        return Stream.of(
                Arguments.of(list(1, 3, 5,0,0,0),
                        list(2, 4, 6), list(1, 2, 3, 4, 5, 6)),
                Arguments.of(list(10, 10, 10,0,0,0),
                        list(10, 10, 10), list(10, 10, 10, 10, 10, 10))
        );
    }

    private static ArrayList<Integer> list(Integer... array) {
        return new ArrayList<>(java.util.Arrays.stream(array).toList());
    }

    static ArrayList<Integer> merge_one_into_another(ArrayList<Integer> first, ArrayList<Integer> second) {
        var m = findActual(first);
        var n = findActual(second);
        var aux = new ArrayList<Integer>();
        var firstIndex = 0;
        var secondIndex = 0;
        while(firstIndex < m && secondIndex < n){
            if(first.get(firstIndex) <= second.get(secondIndex)){
                aux.add(first.get(firstIndex));
                firstIndex++;
            } else {
                aux.add(second.get(secondIndex));
                secondIndex++;
            }
        }
        // gather phase
        while(firstIndex<m){
            aux.add(first.get(firstIndex));
            firstIndex++;
        }
        while(secondIndex<n){
            aux.add(second.get(secondIndex));
            secondIndex ++;
        }
        // copy back
        for(int i=0;i<first.size();i++){
            first.set(i,aux.get(i));
        }
        return first;
    }

    private static int findActual(ArrayList<Integer> array) {
        for(int i=0;i<array.size();i++){
            if(array.get(i)==0) return i;
        }
        return array.size();
    }

    @ParameterizedTest
    @MethodSource("provideArrays")
    void test_merge_one_into_another(ArrayList<Integer> first, ArrayList<Integer> second, ArrayList<Integer> expected) {
        var actual = merge_one_into_another(first, second);
        assertThat(actual).isEqualTo(expected);
    }

}

