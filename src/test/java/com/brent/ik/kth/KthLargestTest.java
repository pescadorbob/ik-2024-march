package com.brent.ik.kth;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static com.brent.ik.sort.QuickSortLomutosPartition.lomutosPartition;
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
        if(start == end) return ;

        var pivot = lomutosPartition(array,start, end);
        if(pivot==k) return ; // lucky case!
        if(k < pivot){
            helper(array,start,pivot-1,k);
        } else {
            helper(array,pivot+1,end,k);
        }

    }


}
