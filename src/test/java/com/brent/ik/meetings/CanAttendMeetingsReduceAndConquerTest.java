package com.brent.ik.meetings;

import java.util.List;

public class CanAttendMeetingsReduceAndConquerTest extends CanAttendMeetingsAlgorithmTest {



    static CanAttendMeetingsAlgorithm getAlgorithmRealization() {
        return new CanAttendMeetingsAlgorithm() {
            @Override
            Integer can_attend_all_meetings(List<List<Integer>> intervals) {
                for (int i = 0; i < intervals.size(); i++) {
                    var interval1 = intervals.get(i);

                    for (int j = i + 1; j < intervals.size(); j++) {
                        var interval2 = intervals.get(j);
                        if (overlaps(interval1, interval2)) {
                            return 0;
                        }
                    }

                }
                return 1;

            }
        };
    }


    @Override
    CanAttendMeetingsAlgorithm getAlgorithm() {
        return CanAttendMeetingsReduceAndConquerTest.getAlgorithmRealization();
    }
}
