package com.brent.ik.intervals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class MergedIntervalsTest {
    private static Stream<Arguments> input() {
        return Stream.of(
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

    private List<List<Integer>> mergeOverlappingIntervals(List<List<Integer>> intervals) {
        intervals.sort(Comparator.comparingInt(a -> a.get(0)));

        var mergedIntervalsResult = new ArrayList<List<Integer>>();
        List<Integer> currentMergedResult = asList(null,null);

        for (int i = 0; i <= intervals.size() - 1; i++) {
            int START_LOCATION = 0;
            int END_LOCATION = 1;
            int nextStart;
            if (i == intervals.size() - 1) {
                nextStart = Integer.MAX_VALUE;
            } else {
                nextStart = intervals.get(i + 1).get(START_LOCATION);
            }
            int start = intervals.get(i).get(START_LOCATION);
            int end = intervals.get(i).get(END_LOCATION);

            if (i == 0) {
                currentMergedResult.set(START_LOCATION, start);
            }
            // terminate trips up to next start location
            if (nextStart > end) {
                currentMergedResult.set(END_LOCATION, end);
                mergedIntervalsResult.add(currentMergedResult);
                currentMergedResult = asList(nextStart,null);
            }
        }
        return mergedIntervalsResult;

    }
}
