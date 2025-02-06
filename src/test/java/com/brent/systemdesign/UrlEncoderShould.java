package com.brent.systemdesign;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlEncoderShould {
    public static Stream<Arguments> encode_a_url_with_no_collisions() {
        return Stream.of(
                Arguments.of("https://myUrl.com/some/really/long/url","https://minime/000",262143),
                Arguments.of("https://myUrl.com/some/really/long/url","https://minime/0000",16777215)
        );
    }

    @ParameterizedTest
    @MethodSource
    void encode_a_url_with_no_collisions(String url,String expected, long counter){
        // 16777216 % 64 = 0 -> 0
        // 16777216 / 64 = 262144
        // 262144 % 64 = 0 -> 0
        // 262144 / 64 = 4096
        // 4096 % 64 = 0 -> 0
        // 4096 / 64 = 64
        // 64 % 64 = 0 -> 0
        var encoder = new UrlEncoder(counter);

        var actual = encoder.encode(url);

        assertThat(actual).isEqualTo(expected);
    }
    public static class UrlEncoder {
        //                                             1         2         3         4         5         6
        //                                   01234567890123456789012345678901234567890123456789012345678901
        private static final String codes = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        long counter;
        Map<Long,String> urls;
        public UrlEncoder(long counter){
            this.counter = counter;
            urls = new HashMap<>();

        }
        public String encode(String input){
            counter ++;
            var digitList = new ArrayList<Integer>();
            var lastDigit = counter % 64;
            digitList.add((int) lastDigit);
            var remaining = counter / 64;
            while(remaining>=64){
                lastDigit = remaining % 64;
                digitList.add((int) lastDigit);
                remaining = remaining / 64;

            }
            Collections.reverse(digitList);
            StringBuilder encodedValue = new StringBuilder();
            for (int i = 0; i < digitList.size(); i++) {
                encodedValue.append(digitList.get(i));
            }
            return String.format("https://minime/%s", encodedValue);
        }
    }
}
