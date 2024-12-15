package com.brent.ik.sum;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.sort;
import static org.assertj.core.api.Assertions.assertThat;

public class ThreeSumCombinationsPreSortedTest {

    public static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(asList(1, 1, 2, 2, 3, 3, 4, 4, 5, 5), 8, 20),
                Arguments.of(asList(1, 1, 2, 2, 2, 2), 5, 12)
        );
    }

    @ParameterizedTest
    @MethodSource("input")
    void shouldCountPairsOfNumbersAddToGivenTargetIncludingAllCombinations(List<Integer> nums, int target, int expected) {
        var actual = threeSumCountWithCombinations(nums, target);
        assertThat(actual).isEqualTo(expected);
    }

    private int threeSumCountWithCombinations(List<Integer> nums, int target) {
        sort(nums);
        var count = 0;
        for (int m = 1; m < nums.size(); m++) {
            count += twoSumCountWithCombinations(m - 1, nums, target - nums.get(m));
        }
        return count;
    }

    private int twoSumCountWithCombinations(int m, List<Integer> nums, int target) {
        int i = 0;
        int j = m;
        var count = 0;
        while (i < j) {
            if (nums.get(i) + nums.get(j) == target) {
                var iCount = lookAhead(nums, i, nums.get(i));
                var jCount = lookBehind(nums, j, nums.get(j));
                if ((int) nums.get(i) == nums.get(j)) {
                    count += nChooseTwo(i, j);
                    break;  // i and j crossed
                } else {
                    count += iCount * jCount;
                }
                i += iCount;
                j -= jCount;
            } else if (nums.get(i) + nums.get(j) > target) {
                j--; // go left to get lower number
            } else {
                i++; // go right to get higher number
            }
        }
        return count;
    }

    private int nChooseTwo(int i, int j) {
        return (j - i + 1) * (j - i) / 2;
    }

    private int lookAhead(List<Integer> nums, int i, Integer val) {
        var iCount = 0;
        while ((int) nums.get(i) == val && i < nums.size()) {
            i++;
            iCount++;
        }
        return iCount;
    }

    private int lookBehind(List<Integer> nums, int j, Integer val) {
        var jCount = 0;
        while ((int) nums.get(j) == val && j > 0) {
            j--;
            jCount++;

        }
        return jCount;
    }

}
