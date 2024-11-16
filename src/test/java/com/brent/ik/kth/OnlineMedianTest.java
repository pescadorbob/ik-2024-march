package com.brent.ik.kth;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class OnlineMedianTest {
    private static Stream<Arguments> provideArrays() {
        return Stream.of(
                Arguments.of(list(1, 3, 5),
                        list(9, 3, 1, 2),
                        list(4, 3, 3, 3))
        );
    }
    /*
      1,3,5,9=> 4
      1,3,3,5,9=> 3
      1,1,3,3,5,9=> 3
      1,1,2,3,3,5,9=> 3
     */

    private static ArrayList<Integer> list(Integer... array) {
        return new ArrayList<>(java.util.Arrays.stream(array).toList());
    }

    @ParameterizedTest
    @MethodSource("provideArrays")
    void test_online_median(ArrayList<Integer> initialArray, ArrayList<Integer> stream, ArrayList<Integer> expected) {
        var onlineMedian = new OnlineMedian(initialArray);
        var actual = new ArrayList<Integer>();
        for (int ele : stream) {
            actual.add(onlineMedian.add(ele));
        }

        assertThat(actual).isEqualTo(expected);
    }




    private class OnlineMedian {
        PriorityQueue<Integer> minHeap;
        PriorityQueue<Integer> maxHeap;

        public OnlineMedian(ArrayList<Integer> initialArray) {
            minHeap = new PriorityQueue<>();
            /*
              comparator. negative number if the first number comes before the second.
              e.g. numbers 10 & 11. if 10 should come before 11, return a negative number
              so the comparator should use this arithmetic: 10 - 11
              so if a = 10 and b = 11
              then the comparator is (a,b)-> a-b which is the default.
              if 11 should come before 10, i.e. a max heap, then 11 - 10
              then the comparator is (a,b)->b-a
             */
            maxHeap = new PriorityQueue<>((a,b)->b-a);
            for(int ele:initialArray){
                add(ele);
            }
        }

        public Integer add(int ele) {
            var median = calculateMedian();
            if(ele < median){
                placeOnMaxHeap(ele);
            } else {
                placeOnMinHeap(ele);
            }
            return calculateMedian();
        }

        private void placeOnMaxHeap(int ele) {
            maxHeap.add(ele);
            if(maxHeap.size()- minHeap.size()==2){
                minHeap.add(maxHeap.poll());
            }
        }

        private void placeOnMinHeap(int ele) {
            minHeap.offer(ele);
            if(minHeap.size()- maxHeap.size() == 2){
                maxHeap.add(minHeap.poll());
            }
        }

        private Integer calculateMedian() {
            if(minHeap.size() == maxHeap.size()){
                if(minHeap.size()==0){
                    return 0;
                }
                return (minHeap.peek() + maxHeap.peek())/2;
            } else if(minHeap.size()>maxHeap.size()){
                return minHeap.peek();
            } else {
                return maxHeap.peek();
            }
        }
    }
}
