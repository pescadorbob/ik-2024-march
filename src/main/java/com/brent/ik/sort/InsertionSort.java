package com.brent.ik.sort;

import java.util.ArrayList;

public class InsertionSort implements Sorter {
    private static String items(ArrayList<Integer> items) {
        String printout = "";
        for (int item : items) {
            printout += item + ",";
        }
        return printout;
    }

    public ArrayList sort(ArrayList<Integer> arr) {
        for (int i = 0; i <= arr.size() - 1; i++) {
            int temp = arr.get(i);
            int red = i - 1;
            while (red >= 0 && temp < arr.get(red)) {
                //shift right
                arr.set(red + 1, arr.get(red));
                red--;
            }
            arr.set(red + 1, temp);
        }
        return arr;
    }


}
