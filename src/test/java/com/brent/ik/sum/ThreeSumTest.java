package com.brent.ik.sum;

import org.junit.jupiter.api.Test;
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
                Arguments.of(asList(-1,2,1,-4), 1, 2)
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
        return helper(nums, target, 0, nums.size() - 2, nums.size() - 1);
    }

    private int helper(List<Integer> nums, int target, int i, int j, int k) {
        var closest = Integer.MAX_VALUE;
        while (k > 1) {
            var localTarget = target - nums.get(k);
            while (i < j) {
                var sum = nums.get(i) + nums.get(j);
                if (sum < localTarget) {

                    // increment i to get bigger numbers
                    closest = updateClosest(closest, sum + nums.get(k), target);
                    i++;
                } else if (sum == localTarget) // lucky!
                {
                    return sum + nums.get(k);
                } else {
                    // decrement j to get smaller numbers
                    closest = updateClosest(closest, sum + nums.get(k), target);
                    j--;
                }
            }
            k--;
            i = 0;
            j = k - 1;
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
