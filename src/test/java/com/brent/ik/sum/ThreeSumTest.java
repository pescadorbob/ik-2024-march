package com.brent.ik.sum;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static java.lang.Math.abs;
import static java.util.Arrays.asList;
import static java.util.Collections.sort;
import static org.assertj.core.api.Assertions.assertThat;

public class ThreeSumTest {
    public static Stream<Arguments> inputs() {
        return Stream.of(
                Arguments.of(asList(0, 1, 2, 4, -15, -40), 0, 3),
                Arguments.of(asList(-1, 2, 1, -4), 1, 2),
                Arguments.of(asList(0, 0, 0), 1, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("inputs")
    void shouldProduceClosest3Sum_givenNums(List<Integer> nums, int target, int expected) {
        var actual = threeSumClosest(nums, target);
        assertThat(actual).isEqualTo(expected);
    }

    private int threeSumClosest(List<Integer> nums, int target) {
        sort(nums);
        var closest = Integer.MAX_VALUE;
        for (int m = 2; m < nums.size(); m++) {
            int i=0;
            int j=m-1;
            var localTarget = target - nums.get(m);
            while (i < j) {
                var sum = nums.get(i) + nums.get(j);
                if (sum < localTarget) {

                    // increment i to get bigger numbers
                    closest = updateClosest(closest, sum + nums.get(m), target);
                    i++;
                } else if (sum == localTarget) // lucky!
                {
                    return sum + nums.get(m);
                } else {
                    // decrement j to get smaller numbers
                    closest = updateClosest(closest, sum + nums.get(m), target);
                    j--;
                }
            }
        }
        return closest;
    }

    private int updateClosest(int incumbent, int contender, int target) {
        var incumbentDistance = abs(target - incumbent);
        var contenderDistance = abs(target - contender);
        if (incumbentDistance < contenderDistance) {
            return incumbent;
        } else return contender;
    }
}
