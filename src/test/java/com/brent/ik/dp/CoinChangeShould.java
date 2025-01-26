package com.brent.ik.dp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.lang.Math.min;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Coin Change Should")
public class CoinChangeShould {
    @Test
    void produce_the_minimum_number_of_coins_needed_to_make_change_given_change_and_denominations(){
        var neededChange = 10;
        var denominations = new int[]{1,5,7};
        var expected = 2;
        var actual = minCoins(neededChange,denominations);
        assertThat(actual).isEqualTo(expected);
    }

    private Integer minCoins(int neededChange, int[] denominations) {
        var solutionCache = new Integer[neededChange+1];
        solutionCache[0] = 0;
        for (int i = 1; i < solutionCache.length; i++) {
            solutionCache[i]=null;
        }
        for (int i = 1; i <= neededChange; i++) {
            // compute and cache f(i)
            var minValue = Integer.MAX_VALUE;
            for(int c:denominations){
                if(i-c>=0){
                    minValue = min(minValue,solutionCache[i-c]);
                }
            }
            solutionCache[i] = 1 + minValue;
        }
        return solutionCache[neededChange];
    }

}
