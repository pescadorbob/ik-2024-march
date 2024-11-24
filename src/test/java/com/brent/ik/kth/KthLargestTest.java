package com.brent.ik.kth;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

import static java.util.Collections.swap;
import static org.assertj.core.api.Assertions.assertThat;

public class KthLargestTest {
    private static Stream<Arguments> provideArrays() {
        return Stream.of(
                Arguments.of(list(1, 3, 5),2,3),
                Arguments.of(list(10, 10, 10),2,10),
                Arguments.of(list(9,33,4,22,11,44,55,66,1,2,3),2,55)
        );
    }

    private static ArrayList<Integer> list(Integer... array) {
        return new ArrayList<>(java.util.Arrays.stream(array).toList());
    }
    @ParameterizedTest
    @MethodSource("provideArrays")
    void test_kth_largest(ArrayList<Integer> array, Integer k, Integer expected) {
        var actual = kthLargest(array, k);
        assertThat(actual).isEqualTo(expected);
    }

    private Integer kthLargest(ArrayList<Integer> array, Integer k) {
        helper(array,0,array.size()-1,array.size()-k);
        return array.get(array.size()-k);
    }
    private void helper(ArrayList<Integer> array, Integer start, Integer end, Integer k){
        // base case
        if(Objects.equals(start, end)) return ;

        var pivotIndex = lomutosPartition(array,start, end);
        if(pivotIndex==k) return ; // lucky case!
        if(k < pivotIndex){
            helper(array,start,pivotIndex-1,k);
        } else {
            helper(array,pivotIndex+1,end,k);
        }

    }
    public static int lomutosPartition(ArrayList<Integer> arr, int start, int end) {
        int pivotIndex = pickPivot(start, end);
        int pivot = arr.get(pivotIndex);
        int smaller = start;
        int larger = start ;
        swap(arr, pivotIndex, start);
        while (larger <= end) {
            if (arr.get(larger) < pivot) {
                smaller++;
                swap(arr, smaller, larger);
            }
            larger++;

        }
        swap(arr, start, smaller);
        return smaller;
    }
    private static int pickPivot(int from,int to){
        return new Random(System.currentTimeMillis()).nextInt(from,to);
    }


}
