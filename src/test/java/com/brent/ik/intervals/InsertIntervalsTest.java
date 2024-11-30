package com.brent.ik.intervals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Integer.max;
import static java.lang.Math.min;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

/*
this solution just uses one result and merges the next interval with the last one in the result.
 */
public class InsertIntervalsTest {
    private static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(asList(asList(1, 3), asList(6,9)), asList(2,5), asList(asList(1, 5),asList(6,9))),
                Arguments.of(asList(asList(2, 3), asList(6,9)), asList(2,5), asList(asList(2, 5),asList(6,9))),
                Arguments.of(asList(asList(1,2),asList(3,5),asList(6,7),asList(8,10),asList(12,16)),asList(4,8),asList(asList(1,2),asList(3,10),asList(12,16)))
        );
    }

    @MethodSource("input")
    @ParameterizedTest
    void shouldInserIntervals_givenSortedIntervalsAndOneToInsert(List<List<Integer>> intervals,List<Integer> insert, List<List<Integer>> expected) {
        var actual = helper(intervals,insert);
        assertThat(actual).isEqualTo(expected);
    }

    int START = 0;
    int END = 1;
    private List<List<Integer>> helper(List<List<Integer>> intervals,List<Integer> insert) {
        // quiet perios
        var result = new ArrayList<List<Integer>>();
        int bailIndex = 0;
        for(int i=0;i<intervals.size();i++){
            if(intervals.get(i).get(END) < insert.get(START)){
                // NO_OVERLAP
                result.add(intervals.get(i));
            } else {
                bailIndex = i;
                break;
            }
        }
        result.add(insert);

        for (int i = bailIndex; i <= intervals.size() - 1; i++) {
            if(getLast(result).get(END) < intervals.get(i).get(START) ||
                    intervals.get(i).get(END)< getLast(result).get(START)){
                // NO OVERLAP
                result.add(intervals.get(i));
            } else {
                mergeIntervals(result.get(last(result)),intervals.get(i));
            }
        }
        return result;

    }

    private void mergeIntervals(List<Integer> last, List<Integer> next) {
        // result[-1] = (result[-1][0], max(result[-1][1],intervals[i][1]
        last.set(START,min(last.get(START),next.get(START)));
        last.set(END,max(last.get(END),next.get(END)));
    }
    private void mergeIntervals_existing(List<Integer> last, List<Integer> next) {
        // result[-1] = (result[-1][0], max(result[-1][1],intervals[i][1]
        last.set(START,last.get(START));
        last.set(END,max(last.get(END),next.get(END)));
    }

    private int last(List<List<Integer>> list){
        return list.size()-1;
    }

    private List<Integer> getLast(ArrayList<List<Integer>> result) {
        return result.get(result.size()-1);
    }
}
