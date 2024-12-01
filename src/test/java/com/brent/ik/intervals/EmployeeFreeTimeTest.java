package com.brent.ik.intervals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Collections.addAll;
import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeFreeTimeTest {
    private static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(schedules(
                        schedule(anInterval(1, 2), anInterval(5, 6)),
                        schedule(anInterval(1,3)),
                        schedule(anInterval(4,10))),
                        schedule(anInterval(3,4))),
                Arguments.of(schedules(
                        schedule(anInterval(1, 3), anInterval(6, 7)),
                        schedule(anInterval(2,4)),
                        schedule(anInterval(2,5),anInterval(9,12))),
                        schedule(anInterval(5,6),anInterval(7,9)))
        );
    }

    @MethodSource("input")
    @ParameterizedTest
    void shouldCreateEmployeeFreeTime_givenWorkTimeIntervalLists(List<List<Interval>> schedules,List<Interval> expected) {

        var actual = freeTime(schedules);
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);

    }
    @Test
    void shouldCreateEmployeeFreeTime_givenWorkTimeIntervals() {
        var schedules = schedules(
                schedule(anInterval(1, 2), anInterval(5, 6)),
                schedule(anInterval(1,3)),
                schedule(anInterval(4,10)));


        var expected = schedule(anInterval(3,4));
        var actual = freeTime(schedules);
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);

    }

    private static ArrayList<List<Interval>> schedules(List<Interval>... schedules) {
        var result = new ArrayList<List<Interval>>();
        addAll(result,schedules);
        return result;
    }

    private static ArrayList<Interval> schedule(Interval ... intervals) {
        var expected = new ArrayList<Interval>();
        addAll(expected,intervals);
        return expected;
    }

    static List<Interval> freeTime(List<List<Interval>> schedules) {
        var algo = new EmployeeFreeTime();
        return algo.freeTimeImpl(schedules);
    }


    static class EmployeeFreeTime {


        private List<Interval> freeTimeImpl(List<List<Interval>> schedules) {
            var minHeap = employeeScheduleMinHeap();
            for (List<Interval> employeeSchedule : schedules) {
                var firstElement = getNextIntervalFromEmployeeSchedule(employeeSchedule,0);
                minHeap.add(firstElement,employeeSchedule,1);
            }
            var result = new ArrayList<Interval>();
            while (!minHeap.isEmpty()) {
                var tracker = minHeap.remove();
                var interval = tracker.interval;
                var schedule = tracker.employeeSchedule;
                merge(result, interval);
                var nextElement = getNextIntervalFromEmployeeSchedule(schedule,tracker.index);

                if (nextElement != null) {
                    minHeap.add(nextElement,schedule,tracker.index+1);
                }
            }
            return calculateIntervalGaps(result);

        }

        private EmployeeScheduleMinHeap employeeScheduleMinHeap() {
            return new EmployeeScheduleMinHeap();
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

        private Interval getNextIntervalFromEmployeeSchedule(List<Interval> schedule,int index) {
            if (index < schedule.size()) {
                return schedule.get(index);
            } else {
                return null;
            }
        }

        private class EmployeeScheduleMinHeap {

            PriorityQueue<EmployeeScheduleTracker> minHeap;

            public EmployeeScheduleMinHeap() {
                minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a.interval.start));
            }

            public void add(Interval element, List<Interval> employeeSchedule, int index) {
                var tracker = new EmployeeScheduleTracker(element,employeeSchedule,index);
                minHeap.add(tracker);
            }

            public boolean isEmpty() {
                return minHeap.isEmpty();
            }

            public EmployeeScheduleTracker remove() {
                return minHeap.remove();
            }

            private class EmployeeScheduleTracker {
                private final Interval interval;
                private final List<Interval> employeeSchedule;
                private final int index;

                public EmployeeScheduleTracker(Interval interval, List<Interval> employeeSchedule, int index) {
                    this.interval = interval;
                    this.employeeSchedule = employeeSchedule;
                    this.index = index;
                }
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
