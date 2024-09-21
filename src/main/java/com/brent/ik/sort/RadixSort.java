package com.brent.ik.sort;

import java.util.ArrayList;
import java.util.HashMap;

public class RadixSort {

    static ArrayList<Integer> radix_sort(ArrayList<Integer> arr) {
        int numberOfDigits = getNumberOfDigits(arr);

        ArrayList<Integer> interimAnswer = arr;
        for (int i = 1; i <= numberOfDigits; i++) {
            interimAnswer = sortByPlace(i, interimAnswer);
        }
        // Write your code here.
        return interimAnswer;
    }

    static ArrayList<Integer> sortByPlace(int place, ArrayList<Integer> arr) {
        var sortedByPlaceList = new HashMap<Integer, ArrayList<Integer>>();
        for (int num : arr) {
            int numAtPlace = numAtPlace(num, place);
            addNumToSortedByPlace(numAtPlace,num, sortedByPlaceList);
        }
        var sortedByPlaceArray = new ArrayList<Integer>();
        for(int key : sortedByPlaceList.keySet()){
            var list = sortedByPlaceList.get(key);
            for(int num : list){
                sortedByPlaceArray.add(num);
            }
        }
        return sortedByPlaceArray;

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

    static int numAtPlace(int num, int place) {
        int interimNum = num;
        int lastDigit = num % 10;
        for (int i = 0; i < place; i++) {
            lastDigit = num % 10;
            interimNum = interimNum / 10;
        }
        return lastDigit;
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
