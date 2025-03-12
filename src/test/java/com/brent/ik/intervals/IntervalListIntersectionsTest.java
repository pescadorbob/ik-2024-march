package com.brent.ik.intervals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class IntervalListIntersectionsTest {

    private static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(asList(asList(1,3), asList(5,9)),
                        asList(),
                        asList()),
                Arguments.of(asList(),
                        asList(asList(4,8), asList(10,12)),
                        asList()),
                Arguments.of(asList(asList(1,7)),
                        asList( asList(3,10)),
                        asList(asList(3,7))),
                Arguments.of(asList(asList(0, 2), asList(5,5), asList(6, 10), asList(13, 23), asList(24, 25)),
                        asList(asList(1, 5), asList(8, 12), asList(15, 24), asList(25, 26)),
                        asList(asList(1, 2), asList(5, 5), asList(8, 10), asList(15, 23), asList(24, 24), asList(25, 25))),
                Arguments.of(asList(asList(0, 2), asList(5, 10), asList(13, 23), asList(24, 25)),
                        asList(asList(1, 5), asList(8, 12), asList(15, 24), asList(25, 26)),
                        asList(asList(1, 2), asList(5, 5), asList(8, 10), asList(15, 23), asList(24, 24), asList(25, 25)))
        );
    }

    @MethodSource("input")
    @ParameterizedTest
    void shouldFindIntersectedIntervals_GivenDisjointedAndSortedSets(List<List<Integer>> f,List<List<Integer>> s,List<List<Integer>> expected) {

        var actual = intersectIntervals(f, s);
        assertThat(actual).isEqualTo(expected);
    }

    private static int START = 0;
    private static int END = 1;

    static List<List<Integer>> intersectIntervals(List<List<Integer>> f, List<List<Integer>> s) {
        int i = 0, j = 0;
        var result = new ArrayList<List<Integer>>();
        while (i < f.size() && j < s.size()) {
            // case 1: first lies entirely before second
            if (f.get(i).get(END) < s.get(j).get(START)) {
                i++;
                // case 2: second lies entirely before second
            } else if (s.get(j).get(END) < f.get(i).get(START)) {
                j++;
                // case 3: they overlap
            } else {
                result.add(asList(max(f.get(i).get(START), s.get(j).get(START)),
                        min(f.get(i).get(END), s.get(j).get(END))));
                // move the pointer for which ever ends first.
                if (f.get(i).get(END) < s.get(j).get(END)) {
                    i++;
                } else {
                    j++;
                }
            }
        }
        return result;
    }
}
