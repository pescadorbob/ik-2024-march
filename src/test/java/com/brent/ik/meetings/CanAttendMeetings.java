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


    static Integer can_attend_all_meetings(ArrayList<ArrayList<Integer>> intervals) {
        for(int i=0;i<intervals.size();i++){
            var interval1 = intervals.get(i);

            for(int j=i+1;j<intervals.size();j++){
                var interval2 = intervals.get(j);
                if(overlaps(interval1,interval2)) return 0;
            }

        }
        return 1;
    }
    static boolean overlaps(ArrayList<Integer> interval1,ArrayList<Integer> interval2){
        int start = 0;
        int end = 1;
        return interval1.get(start) < interval2.get(end) &&
           interval2.get(start) < interval1.get(end);

    }


}
