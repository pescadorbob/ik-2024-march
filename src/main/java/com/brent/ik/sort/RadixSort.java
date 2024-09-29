package com.brent.ik.sort;

import java.util.ArrayList;
import java.util.HashMap;

public class RadixSort {

    static ArrayList<Integer> radix_sort(ArrayList<Integer> arr) {
        int numberOfDigits = getNumberOfDigits(arr);

        ArrayList<Integer> interimAnswer = arr;
        for (long place = 1; place <= Math.pow(10,numberOfDigits); place*=10) {
            interimAnswer = sortByPlace(place, interimAnswer);
        }

        return interimAnswer;
    }
    /*
      arr: 5,8,3,9,4,11,7
      expected: 3,4,5,7,8,9,11
      numberOfDigits: 2
      interim: 5,8,3,9,4,11,7
      stack 1
          place:1
          arr: 5,8,3,9,4,11,7
          sortedByPlaceList
      interim: 11,3,4,5,7,8,9
          place:2
          arr: 11,3,4,5,7,8,9
          sortedByPlaceList
              place:2
              arr: 11,3,4,5,7,8,9
              sortedByPlaceList:[]
     */

    static ArrayList<Integer> sortByPlace(long place, ArrayList<Integer> arr) {
        var sortedByPlaceList = new HashMap<Integer, ArrayList<Integer>>();
        addNumbersToListsByPlace(place, arr, sortedByPlaceList);
        return flatten(sortedByPlaceList);

    }
    /*
                            0  1  2  3  4  5   6  7  8  9
         sortedByPlaceList: [],[11],[],[3],[4],[5],[7],[],[8],[9]
         11,3,4,5,7,8,9
     */

    private static ArrayList<Integer> flatten(HashMap<Integer, ArrayList<Integer>> sortedByPlaceList) {
        var sortedByPlaceArray = new ArrayList<Integer>();
        for(int key : sortedByPlaceList.keySet()){
            var list = sortedByPlaceList.get(key);
            for(int num : list){
                sortedByPlaceArray.add(num);
            }
        }
        return sortedByPlaceArray;
    }

    /*
     place: 1
     arr: 5,8,3,9,4,11,7
     sortedByPlaceList:
                            0  1  2  3  4  5   6  7  8  9
         sortedByPlaceList: [],[11],[],[3],[4],[5],[7],[],[8],[9]
     place: 2
     arr: 11,3,4,5,7,8,9
     sortedByPlaceList:
                            0  1  2  3  4  5   6  7  8  9
         sortedByPlaceList: [3,4,5,7,8,9],[11],[],[],[],[],[],[],[],[]
     */
    private static void addNumbersToListsByPlace(long place, ArrayList<Integer> arr, HashMap<Integer, ArrayList<Integer>> sortedByPlaceList) {
        for (int num : arr) {
            int numAtPlace = numAtPlace(num, place);
            addNumToSortedByPlace(numAtPlace,num, sortedByPlaceList);
        }
    }

    private static void addNumToSortedByPlace(int numAtPlace, int num, HashMap<Integer, ArrayList<Integer>> sortedByPlace) {
        ArrayList<Integer> nums;
        if(sortedByPlace.containsKey(numAtPlace)){
            nums = sortedByPlace.get(numAtPlace);
        } else {
            nums = new ArrayList<>();
            sortedByPlace.put(numAtPlace,nums);
        }
        nums.add(num);
    }

    /*
      num:5
      place: 2
      interim:5
      lastDigit:5
      i:0
        lastDigit:5
        interimNum = 0
      i:1
        lastDigit:0
        interimNum:0
      return 0
      ------
      num:11
      place: 2
      interim:11
      lastDigit:1
      i:0
        lastDigit:1
        interimNum = 1
      i:1
        lastDigit:1
        interimNum:0
      return 1
     */
    static int numAtPlace(int num, long place) {
        long digit = ((long)num/place)%10l;
        return (int) digit;
    }

    static int getNumberOfDigits(ArrayList<Integer> arr) {
        long max = max(arr);
        String maxString = String.format("%d",max);
        return maxString.length();
    }

    static int max(ArrayList<Integer> arr) {
        if (arr.size() == 0) return 0;
        int max = arr.get(0);
        for (int num : arr) {
            max = Math.max(max, num);
        }
        return max;
    }

}
