package com.brent.ik.kth;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static java.lang.Math.abs;
import static java.util.Arrays.asList;
import static java.util.Collections.swap;
import static org.assertj.core.api.Assertions.assertThat;

public class TopKFrequentElementsTest {
    private static Stream<Arguments> provideArrays() {
        return Stream.of(
                Arguments.of(asList(1, 1, 1, 2, 2, 3), 2, asList(1, 2)),
                Arguments.of(asList(1), 1, asList(1))
        );
    }


    @ParameterizedTest
    @MethodSource("provideArrays")
    void testTopKFrequentElements(List<Integer> points, Integer k, List<Integer> expected) {

        var actual = topKFrequentElements(points, k);
        assertThat(actual).containsOnlyElementsOf(expected);
    }


    private List<Integer> topKFrequentElements(List<Integer> points, Integer k) {
        var freq = frequency(points);
        var n = freq.size();
        helper(freq, 0, n - 1,n- k);
        var top = new ArrayList<Integer>();

        for (int i = n-k; i < n; i++) {
            top.add(freq.get(i).get(0));
        }
        return top;
    }
    private static List<List<Integer>> frequency(List<Integer> points){
        var freqM = new HashMap<Integer,Integer>();
        for(int point : points){
            freqM.put(point,freqM.getOrDefault(point,0)+ 1);
        }
        var freq = new ArrayList<List<Integer>>();
        for(int key:freqM.keySet()){
            freq.add(asList(key,freqM.get(key)));
        }
        return freq;
    }

    private static void helper(List<List<Integer>> freq, int start, int end, int k) {
        if (start == end) return;
        int pivot = partition(freq, start, end);
        if (pivot == k) {
            // lucky case!
            return;
        }
        if (k < pivot) {
            helper(freq, start, pivot - 1, k);
        } else {
            helper(freq, pivot + 1, end, k);
        }
    }

    private static int partition(List<List<Integer>> freq, int start, int end) {
        int pIndex = new Random(System.currentTimeMillis()).nextInt(start, end);
        int smaller = start;
        List<Integer> pivot = freq.get(pIndex);
        swap(freq, start, pIndex);

        for (int larger = smaller + 1; larger <= end; larger++) {
            if (compare(freq.get(larger), pivot)) {
                smaller++;
                swap(freq, smaller, larger);
            }
        }
        swap(freq, start, smaller);
        return smaller;
    }

    private static boolean compare(List<Integer> larger, List<Integer> pivot) {
        return larger.get(1) < pivot.get(1);
    }

}
