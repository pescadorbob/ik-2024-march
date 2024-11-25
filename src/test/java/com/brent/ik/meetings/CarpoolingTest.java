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

public class CarpoolingTest {
    private static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(asList(asList(2,1,5), asList(3,3,7)),4,false),
                Arguments.of(asList(asList(2,1,5), asList(3,3,7)),5,true),
                Arguments.of(asList(asList(2,1,5), asList(3,5,7)),3,true)

        );
    }



    @MethodSource("input")
    @ParameterizedTest
    void shouldCalculateCarpoolHasCapacity_givenNumPassenger_start_end_capacity(List<List<Integer>> intervals, int capacity,boolean expected) {

        var actual = carpoolHasCapacity(intervals,capacity);
        assertThat(actual).isEqualTo(expected);
    }

    private boolean carpoolHasCapacity(List<List<Integer>> intervals,Integer capacity) {
        intervals.sort(Comparator.comparingInt(a -> a.get(1)));
            int Q_END_TIME = 0;
            int Q_NUM_PASSENGERS = 1;
        var minHeap = new PriorityQueue<List<Integer>>((a,b)->b.get(Q_END_TIME)-a.get(Q_END_TIME));
        var numPassengersInCar = 0; // how many people are in the car right now.
        for (int i = 0; i <= intervals.size() - 1; i++) {
            // start meeting [num_passengers, start_location, end_location]
            int NUM_PASSENGERS = 0;
            int START_LOCATION = 1;
            int END_LOCATION = 2;
            int nextStart;
            if (i == intervals.size() - 1) {
                nextStart = Integer.MAX_VALUE;
            } else {
                nextStart = intervals.get(i + 1).get(START_LOCATION);
            }
            minHeap.add(asList(intervals.get(i).get(END_LOCATION),intervals.get(i).get(NUM_PASSENGERS)));
            numPassengersInCar += intervals.get(i).get(NUM_PASSENGERS);
            if(numPassengersInCar > capacity){
                return false;
            }

            // terminate trips up to next start location
            while (!minHeap.isEmpty() && minHeap.peek().get(Q_END_TIME) <= nextStart) {
                var carload = minHeap.remove();
                numPassengersInCar -= carload.get(Q_NUM_PASSENGERS);
            }
        }
        return true;
    }
}
