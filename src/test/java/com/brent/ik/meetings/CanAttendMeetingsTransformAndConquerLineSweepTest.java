package com.brent.ik.meetings;

import java.util.Comparator;
import java.util.List;


public class CanAttendMeetingsTransformAndConquerLineSweepTest extends CanAttendMeetingsAlgorithmTest {
    static CanAttendMeetingsAlgorithm getAlgorithmRealization(Metrics metrics){
        return new CanAttendMeetingsAlgorithm() {
            @Override
            Integer can_attend_all_meetings(List<List<Integer>> intervals) {
                // instead of reduce and conquer, let's try to pre-sort it and conquer
                intervals.sort(Comparator.comparingInt(interval -> interval.get(0)));
                metrics.comparisons += (int) (intervals.size() * Math.log(intervals.size()));
                metrics.inputSize = intervals.size();
                for (int i = 0; i < intervals.size() - 1; i++) {
                    var interval1 = intervals.get(i);
                    var interval2 = intervals.get(i + 1);
                    metrics.comparisons++;
                    if (overlaps(interval1, interval2)) return 0;

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
    CanAttendMeetingsAlgorithm getAlgorithm(Metrics metrics) {
        return CanAttendMeetingsTransformAndConquerLineSweepTest.getAlgorithmRealization(metrics);
    }
}
