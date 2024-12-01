package com.brent.ik.intervals;

import com.brent.ik.trees.BST;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.regex.MatchResult;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Collections.addAll;
import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeFreeTimeTest {
    @Test
    void shouldCreateEmployeeFreeTime_givenWorkTimeIntervals() {
        var schedules = new ArrayList<List<Interval>>();
        schedule(schedules, anInterval(1, 2), anInterval(5, 6));
        schedule(schedules, anInterval(1, 3));
        schedule(schedules, anInterval(4,10));


        var expected = schedule(anInterval(3,4));
        var actual = freeTime(schedules);
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);

    }

    private static ArrayList<Interval> schedule(Interval ... intervals) {
        var expected = new ArrayList<Interval>();
        addAll(expected,intervals);
        return expected;
    }

    private static void schedule(ArrayList<List<Interval>> schedules, Interval... intervals) {
        var schedule = new ArrayList<Interval>();
        addAll(schedule, intervals);
        schedules.add(schedule);
    }

    static List<Interval> freeTime(List<List<Interval>> schedules) {
        var algo = new EmployeeFreeTime();
        return algo.freeTimeImpl(schedules);
    }

    static class EmployeeFreeTime {


        Map<List<Interval>, Integer> indexes = new HashMap<>();

        private List<Interval> freeTimeImpl(List<List<Interval>> schedules) {
            var sToIMap = new HashMap<Interval, List<Interval>>();
            var minHeap = new PriorityQueue<Interval>((a, b) -> a.start - b.end);
            for (List<Interval> schedule : schedules) {
                var firstElement = removeFirst(schedule);
                indexes.put(schedule, 1);
                minHeap.add(firstElement);
                sToIMap.put(firstElement, schedule);
            }
            var result = new ArrayList<Interval>();
            while (!minHeap.isEmpty()) {
                var interval = minHeap.remove();
                var schedule = sToIMap.get(interval);
                merge(result, interval);
                var firstElement = removeFirst(schedule);
                if (firstElement != null) {
                    minHeap.add(firstElement);
                    sToIMap.put(firstElement, schedule);
                }
            }
            return calculateIntervalGaps(result);

        }

        private List<Interval> calculateIntervalGaps(List<Interval> intervals) {
            var result = new ArrayList<Interval>();
            for (int i = 0; i < intervals.size() - 1; i++) {
                var newInterval = anInterval(intervals.get(i).end, intervals.get(i + 1).start);
                result.add(newInterval);
            }
            return result;
        }

        private void merge(List<Interval> existing, Interval interval) {
            if (existing.size() == 0) {
                existing.add(interval);
            }
            var last = existing.get(existing.size() - 1);
            if (last.end < interval.start || interval.end < last.start) {
                // no overlap
                existing.add(interval);
            } else {
                mergeIntervals(interval, last);
            }
        }

        private void mergeIntervals(Interval interval, Interval last) {
            last.start = min(last.start, interval.start);
            last.end = max(last.end, interval.end);
        }

        private Interval removeFirst(List<Interval> schedule) {
            var currentIndex = 0;
            if (indexes.containsKey(schedule)) {
                currentIndex = indexes.get(schedule);

            }
            if (currentIndex < schedule.size()) {
                var interval = schedule.get(currentIndex);
                indexes.put(schedule, ++currentIndex);
                return interval;
            } else {
                return null;
            }
        }
    }

    @Test
    void testRemoveMethodOnArrayListShouldRemoveEfficiently() {
        var schedules = new ArrayList<List<Interval>>();
        var schedule = new ArrayList<Interval>();
        schedule.add(anInterval(1, 2));
        schedule.add(anInterval(5, 6));
        var expectedFirstElement = anInterval().start(1).end(2).build();
        var firstElement = schedule.remove(0);
        assertThat(firstElement).isEqualToComparingFieldByField(expectedFirstElement);
        var expectedSchedule = new ArrayList<Interval>();
        expectedSchedule.add(anInterval().start(5).end(6).build());
        assertThat(schedule).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedSchedule);
    }

    static IntervalTestBuilder anInterval() {
        return new IntervalTestBuilder();
    }

    static Interval anInterval(int start, int end) {
        var builder = new IntervalTestBuilder();
        builder.start(start);
        builder.end(end);
        return builder.build();
    }

    static class Interval {
        int start;
        int end;

        public Interval(int start, int end) {

            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "[" + start + "," + end + "]";

        }
    }

    private static class IntervalTestBuilder {
        private int start;
        private int end;

        public IntervalTestBuilder start(int start) {
            this.start = start;
            return this;
        }

        public IntervalTestBuilder end(int end) {
            this.end = end;
            return this;
        }

        public Interval build() {
            return new Interval(start, end);
        }
    }
}
