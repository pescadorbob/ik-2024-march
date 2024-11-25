package com.brent.ik.meetings;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

public abstract class CanAttendMeetingsAlgorithmTest {

    private static Stream<Arguments> testLongIntervalWithTimeout() {
        var transformAndConquerLineSweepAlgoithm = CanAttendMeetingsTransformAndConquerLineSweepTest.getAlgorithmRealization();
        var reduceAndConquerLineSweepAlgoithm = CanAttendMeetingsReduceAndConquerTest.getAlgorithmRealization();
        return Stream.of(
                Arguments.of("Transform and Conquer line sweep",
                        load("com/brent/ik/meetings/input023.txt"),
                        1,
                        false,
                        1,
                        transformAndConquerLineSweepAlgoithm),
                Arguments.of("Reduce and Conquer",
                        load("com/brent/ik/meetings/input023.txt"),
                        1,
                        true,
                        1,
                        reduceAndConquerLineSweepAlgoithm)
        );
    }
    @ParameterizedTest
    @MethodSource
    void testLongIntervalWithTimeout(String algorithmName,
                                     List<List<Integer>> intervals,
                                     int expected,
                                     boolean isTimeoutExpected,
                                     int timeoutValue,
                                     CanAttendMeetingsAlgorithm algorithm) throws Exception {

        var timeoutUnit = TimeUnit.SECONDS;
        try {
            var actual = callWithTimeout(() -> algorithm.can_attend_all_meetings(intervals), timeoutValue, TimeUnit.SECONDS);
            assertThat(actual).isEqualTo(expected);
        } catch (TimeoutException e) {
            if (!isTimeoutExpected) {
                fail(String.format("This timed out with a timeout of %d %s", timeoutValue, timeoutUnit));
            }
        }
    }

    private static Stream<Arguments> testIntervals() {
        return Stream.of(
                Arguments.of(load("com/brent/ik/meetings/input016.txt"), 0),
                Arguments.of(load("com/brent/ik/meetings/input025.json"), 0),
                Arguments.of(new ArrayList<>(asList(list(1, 5), list(5, 8), list(10, 15))), 1),
                Arguments.of(new ArrayList<>(asList(list(1, 5), list(5, 8), list(10, 15))), 1),
                Arguments.of(new ArrayList<>(asList(list(1, 6), list(5, 8), list(10, 15))), 0)
        );
    }

    static List<List<Integer>> load(String fileName) {
        var stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        Objects.requireNonNull(stream);
        try {
            var input = new ObjectMapper().readValue(stream, IntervalTestInput.class);
            return input.intervals;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    static class IntervalTestInput {
        public List<List<Integer>> intervals;
    }


    public static <T> T callWithTimeout(Callable<T> callable, long timeout, TimeUnit unit) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<T> future = executor.submit(callable);
        try {
            return future.get(timeout, unit);
        } catch (TimeoutException e) {
            future.cancel(true); // Attempt to cancel the task
            throw e;
        } finally {
            executor.shutdown();
        }
    }


    @ParameterizedTest
    @MethodSource
    void testIntervals(List<List<Integer>> intervals, int expected) {
        var algorithm = getAlgorithm();
        var actual = algorithm.can_attend_all_meetings(intervals);
        assertThat(actual).isEqualTo(expected);
    }

    abstract CanAttendMeetingsAlgorithm getAlgorithm();

    static abstract class CanAttendMeetingsAlgorithm {
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

    */
        abstract Integer can_attend_all_meetings(List<List<Integer>> intervals);

    }

    static ArrayList<Integer> list(Integer... list) {
        return new ArrayList<>(Arrays.asList(list));
    }


    static boolean overlaps(List<Integer> interval1, List<Integer> interval2) {
        int start = 0;
        int end = 1;
        return interval1.get(end) > interval2.get(start);

    }


}
