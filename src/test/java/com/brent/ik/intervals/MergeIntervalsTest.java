package com.brent.ik.intervals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Integer.max;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

/*
this solution just uses one result and merges the next interval with the last one in the result.
 */
public class MergeIntervalsTest {
    private static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(asList(asList(1, 5), asList(2, 3)), asList(asList(1, 5))),
                Arguments.of(asList(asList(1, 3), asList(2, 6), asList(8, 10), asList(15, 18)), asList(asList(1, 6), asList(8, 10), asList(15, 18))),
                Arguments.of(asList(asList(1, 3), asList(2, 6), asList(8, 10), asList(15, 18), asList(17, 19)), asList(asList(1, 6), asList(8, 10), asList(15, 19))),
                Arguments.of(asList(asList(1, 4), asList(4,5)), asList(asList(1, 5)))
        );
    }

    @MethodSource("input")
    @ParameterizedTest
    void shouldMergeIntervals_givenOverlappingIntervals(List<List<Integer>> intervals, List<List<Integer>> expected) {
        var actual = mergeOverlappingIntervals(intervals);
        assertThat(actual).isEqualTo(expected);
    }

    int START = 0;
    int END = 1;
    private List<List<Integer>> mergeOverlappingIntervals(List<List<Integer>> intervals) {
        intervals.sort(Comparator.comparingInt(a -> a.get(0)));

        var result = new ArrayList<List<Integer>>();
        result.add(intervals.get(0));

        for (int i = 0; i <= intervals.size() - 1; i++) {
            if(getLast(result).get(END) < intervals.get(i).get(START) ||
                    intervals.get(i).get(END)< getLast(result).get(START)){
                // NO OVERLAP
                result.add(intervals.get(i));
            } else {
                getLast(result).set(END,max(getLast(result).get(END),intervals.get(i).get(END)));
            }
        }
        return result;

    }

    private List<Integer> getLast(ArrayList<List<Integer>> result) {
        return result.get(result.size()-1);
    }
}
