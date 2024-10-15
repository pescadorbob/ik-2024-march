package com.brent.ik.meetings;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class CanAttendMeetings {
    private static Stream<Arguments> testIntervals() {
        return Stream.of(
                Arguments.of(new ArrayList<>(asList(list(1, 5), list(5, 8), list(10, 15))),1),
                Arguments.of(new ArrayList<>(asList(list(1, 5), list(5, 8), list(10, 15))),1),
                Arguments.of(new ArrayList<>(asList(list(1, 6), list(5, 8), list(10, 15))),0)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testIntervals(ArrayList<ArrayList<Integer>> intervals, int expected ){
        var actual = can_attend_all_meetings(intervals);
        assertThat(actual).isEqualTo(expected);
    }
    static ArrayList<Integer> list(Integer... list){
        return new ArrayList<>(Arrays.asList(list));
    }
    /*
                      1                   2
  0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0
0   [       ]
        1   [     ]
                    2 [         ]
        if(start <= otherEnd && otherStart < end){
     0 -- 1
     1 <= 8 true && 5 <= 5 true >> true overlaps!
*/

    static Integer can_attend_all_meetings(ArrayList<ArrayList<Integer>> intervals) {
        for(var interval:intervals){
            for(var otherInterval:intervals){
                if(interval!=otherInterval){
                    if( overlaps(interval,otherInterval)) return 0;
                }
            }
        }
        return 1;
    }

    /*
          <------------>
          a____b c_____d
     */
    private static boolean overlaps(ArrayList<Integer> interval, ArrayList<Integer> otherInterval) {
        var start=0;
        var end=1;
        return interval.get(start) < otherInterval.get(end) &&
                otherInterval.get(start)  < interval.get(end);
    }
}
