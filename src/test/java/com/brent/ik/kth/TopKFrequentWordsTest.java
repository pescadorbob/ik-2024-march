package com.brent.ik.kth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.swap;
import static org.assertj.core.api.Assertions.assertThat;

public class TopKFrequentWordsTest {

    private static Stream<Arguments> provideArrays() {
        return Stream.of(
                Arguments.of(asList("i", "love", "leetcode", "i", "love", "coding"),2,asList("i", "love"))
        );
    }


    @ParameterizedTest
    @MethodSource("provideArrays")
    void testShouldReturnTopKFrequentWordsAndSortedLexicographicallyIfSameFrequency_givenWords(List<String> words,int k, List<String>expected) {
        var actual = topKFrequentWords(words, k);
        assertThat(actual).isEqualTo(expected);
    }

    private List<String> topKFrequentWords(List<String> words, int k) {
        var freqPairs = frequencyPairs(words);
        quickSelect(freqPairs, k);
        sortToK(freqPairs, k);
        return selectFirstKWords(freqPairs,k);
    }

    private List<String> selectFirstKWords(List<List> freqPairs, int k) {
        var words = new ArrayList<String>();
        for (int i = 0; i < k; i++) {
            String word = (String) freqPairs.get(i).get(WORD);
            words.add(word);
        }
        return words;
    }

    private void sortToK(List<List> pairs, int k) {
        helperSort(pairs, 0, k);
    }

    private void helperSort(List<List> pairs, int start, int end) {
        if (start >= end) return;
        var pivot = partition(pairs,start,end);
        helperSort(pairs,start,pivot-1);
        helperSort(pairs,pivot+1,end);
    }


    private static void quickSelect(List<List> freqPairs, int k) {
        helper(freqPairs, 0, freqPairs.size() - 1, k);
    }

    private static void helper(List<List> pairs, int start, int end, int k) {
        if (start == end) return;
        var pivot = partition(pairs, start, end);
        if (pivot == k) {
            // lucky guess!
            return;
        } else if (k < pivot) { // left of pivot
            helper(pairs, start, pivot - 1, k);
        } else { // right of pivot
            helper(pairs, pivot + 1, end, k);
        }
    }

    private static final Random random = new Random(System.currentTimeMillis());

    private static int partition(List<List> pairs, int start, int end) {
        int p = random.nextInt(start, end);
        swap(pairs, p, start);
        var s = start; // smaller
        for (var l = s + 1; l <= end; l++) { //l == larger
            if (compare(pairs.get(l), pairs.get(start))) {
                s++;
                swap(pairs, s, l);
            }
        }
        swap(pairs, start, s);
        return s;
    }

    private static int FREQ = 1;
    private static int WORD = 0;

    private static boolean compare(List x1, List x2) {
        if ((Integer) x1.get(FREQ) > (Integer) x2.get(FREQ) ||
                (x1.get(FREQ) == x2.get(FREQ) && ((String) x1.get(WORD)).compareTo((String) x2.get(WORD)) < 0)) {
            return true;
        } else {
            return false;
        }
    }

    private static List<List> frequencyPairs(List<String> words) {
        var map = new HashMap<String, Integer>();
        for (var word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        var pairs = new ArrayList();
        map.keySet().forEach(key -> pairs.add(asList(key, map.get(key))));
        return pairs;
    }

}
