package com.brent.ik.intervals;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class IntervalListIntersectionsTest {

    @Test
    void shouldFindIntersectedIntervals_GivenDisjointedAndSortedSets() {
        var f = asList(asList(0, 2), asList(5, 10), asList(13, 23), asList(24, 25));
        var s = asList(asList(1, 5), asList(8, 12), asList(15, 24), asList(25, 26));
        var expected = asList(asList(1, 2), asList(5, 5), asList(8, 10), asList(15, 23), asList(24, 24), asList(25, 25));

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
