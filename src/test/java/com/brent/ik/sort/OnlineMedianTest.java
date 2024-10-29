package com.brent.ik.sort;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

public class OnlineMedianTest {
    

    private static Stream<Arguments> inputs() {

        return Stream.of(
                of(list(3, 4, 8, 12, 0),list(3,3,4,6,4)),
                of(list(53, 4, 58, 12, 0),list(53,28,53,32,12)
                        ));

    }

    private static ArrayList<Integer> list(Integer ... nums) {
        return new ArrayList<>(Arrays.stream(nums).toList());

    }

    @ParameterizedTest
    @MethodSource("inputs")
    void shouldComputeMedianAfterEachElement(ArrayList<Integer> input, ArrayList<Integer> expected) {
        var actualOutput = online_median(input);
        assertThat(actualOutput).isEqualTo(expected);
    }
    @ParameterizedTest
    @MethodSource("inputs")
    void shouldComputeMedianAfterEachElementHeap(ArrayList<Integer> input, ArrayList<Integer> expected) {
        var actualOutput = online_median_heap(input);
        assertThat(actualOutput).isEqualTo(expected);
    }

    private ArrayList<Integer> online_median_heap(ArrayList<Integer> input) {
        var medians = new ArrayList<Integer>();
        var smallerHeap = createMaxHeap();
        var biggerHeap = createMinHeap();
        for(int val:input){

            addValue(val, smallerHeap, biggerHeap);
            medians.add(getMedianFromHeaps(smallerHeap,biggerHeap));

        }
        return medians;
    }

    private Integer getMedianFromHeaps(PriorityQueue<Integer> smallerHeap, PriorityQueue<Integer> biggerHeap) {
        if(smallerHeap.size() == biggerHeap.size()){
            return (smallerHeap.peek() + biggerHeap.peek())/2;
        } else {
            return smallerHeap.peek();
        }
    }

    private static void addValue(int val, PriorityQueue<Integer> smallerHeap, PriorityQueue<Integer> biggerHeap) {
        smallerHeap.add(val);
        biggerHeap.add(smallerHeap.poll());


        // maintain size
        if(biggerHeap.size() > smallerHeap.size()){
            smallerHeap.add(biggerHeap.poll());

        }
    }

    private PriorityQueue<Integer> createMinHeap() {
        return new PriorityQueue<>((a,b)->b-a);
    }

    private PriorityQueue<Integer> createMaxHeap() {
        return new PriorityQueue<>();
    }

    static ArrayList<Integer> online_median(ArrayList<Integer> stream) {
        var response = new ArrayList<Integer>();
        var interim = new ArrayList<Integer>();
        for (int val:stream){
            interim.add(val);
            Collections.sort(interim);
            if(isOdd(interim)){
                response.add(interim.get(interim.size()/2));
            } else {
                var smaller = interim.get(interim.size()/2-1);
                var larger = interim.get(interim.size()/2);
                response.add((smaller+larger)/2);
            }
        }
        return response;
    }

    private static boolean isOdd(ArrayList<Integer> interim) {
        return interim.size() % 2 == 1;
    }
}