package com.brent.ik.meetings;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Stream;

import static java.lang.Math.max;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class RequiredConcurrentMeetingsTest {
    private static Stream<Arguments> shouldCalculateConcurrentMeetings_givenOverlappingMeetings() {
        return Stream.of(
                Arguments.of(asList(asList(0, 5), asList(1, 6), asList(2, 7), asList(9, 10)),3),
                Arguments.of(asList(asList(0, 30), asList(5,10), asList(15,20)),2),
                Arguments.of(asList(asList(0, 5), asList(5, 6), asList(6, 7), asList(9, 10)),1)
        );
    }



    @MethodSource
    @ParameterizedTest
    void shouldCalculateConcurrentMeetings_givenOverlappingMeetings(List<List<Integer>> intervals, int expected) {

        var actual = concurrentMeetings(intervals);
        assertThat(actual).isEqualTo(expected);
    }

    private int concurrentMeetings(List<List<Integer>> intervals) {
        intervals.sort(Comparator.comparingInt(a -> a.get(0)));
        var minHeap = new PriorityQueue<Integer>();
        var globalMax = 0;
        for (int i = 0; i <= intervals.size() - 1; i++) {
            int nextStart;
            if (i == intervals.size() - 1) {
                nextStart = Integer.MAX_VALUE;
            } else {
                nextStart = intervals.get(i + 1).get(0);
            }
            // start meeting
            minHeap.add(intervals.get(i).get(1));
            globalMax = max(globalMax, minHeap.size());

            // end all meetings that end before the start of the next one
            while (!minHeap.isEmpty() && minHeap.peek() <= nextStart) {
                minHeap.remove();
            }
        }
        return globalMax;
    }
}
