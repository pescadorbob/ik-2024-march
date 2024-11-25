package com.brent.ik.meetings;

import java.util.Comparator;
import java.util.List;


public class CanAttendMeetingsTransformAndConquerLineSweepTest extends CanAttendMeetingsAlgorithmTest {
    static CanAttendMeetingsAlgorithm getAlgorithmRealization() {
        return new CanAttendMeetingsAlgorithm() {
            @Override
            Integer can_attend_all_meetings(List<List<Integer>> intervals) {

                intervals.sort(Comparator.comparingInt(interval -> interval.get(0)));
                for (int i = 0; i < intervals.size() - 1; i++) {
                    // handle intervals[i] up to the start of the next interval -> intervals[i+1][0]
                    int nextStart;
                    if (i == intervals.size() - 1) {
                        nextStart = Integer.MAX_VALUE;
                    } else {
                        nextStart = intervals.get(i + 1).get(0);
                    }
                    // start my interval
                    if (intervals.get(i).get(1) > nextStart) return 0;
                    // end all intervals before start of next interval
                }
                return 1;
            }
        };

    }

    /*
case 1: Overlap occurs
1   ******
2     ********
3       ********
4                      ********
case 2: Overlap doesn't occur
1   ******
2          ********
3                   **
4                      ********
                   1                   2
0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0

for each interval, after you sort it,
handle intervals up to the start of the next interval.
*/
    @Override
    CanAttendMeetingsAlgorithm getAlgorithm() {
        return CanAttendMeetingsTransformAndConquerLineSweepTest.getAlgorithmRealization();
    }
}
