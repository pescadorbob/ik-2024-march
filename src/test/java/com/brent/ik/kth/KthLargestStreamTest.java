package com.brent.ik.kth;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class KthLargestStreamTest {
    private static Stream<Arguments> provideArrays() {
        return Stream.of(
                Arguments.of(list(1, 3, 5), list(9, 3, 1, 2), list(3, 3, 3, 3), 3)
        );
    }

    private static ArrayList<Integer> list(Integer... array) {
        return new ArrayList<>(java.util.Arrays.stream(array).toList());
    }

    @ParameterizedTest
    @MethodSource("provideArrays")
    void test_kth_largest_stream(ArrayList<Integer> initialArray, ArrayList<Integer> stream, ArrayList<Integer> expected, Integer k) {
        var kthStream = new KthStream(initialArray, k);
        var actual = new ArrayList<Integer>();
        for (int ele : stream) {
            actual.add(kthStream.add(ele));
        }

        assertThat(actual).isEqualTo(expected);
    }


    private class KthStream {
        private final Integer k;
        PriorityQueue<Integer> minHeap;

        public KthStream(ArrayList<Integer> initialArray, Integer k) {
            this.k = k;
            minHeap = new PriorityQueue<>();
            minHeap.addAll(initialArray);
            while(minHeap.size()>k){
                minHeap.remove();
            }
        }

        public Integer add(int ele) {
            minHeap.add(ele);
            while(minHeap.size()>k){
                minHeap.remove();
            }
            return minHeap.peek();
        }
    }
}
