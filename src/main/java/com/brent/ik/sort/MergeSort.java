package com.brent.ik.sort;

import java.util.ArrayList;
import java.util.Objects;


public class MergeSort implements Sorter {
    public ArrayList<Integer> sort(ArrayList<Integer> arr) {
        helper(arr,0,arr.size()-1);
        return arr;
    }
    private void helper(ArrayList<Integer> arr,int start, int end){
        if(start == end) return ;
        int mid = start + ((end-start)/2);
        helper(arr,start,mid);
        helper(arr,mid+1,end);

        var aux = new ArrayList<Integer>();
        int l = start;
        int r = mid+1;
        while(l<=mid && r<=end){
            if(arr.get(l) <= arr.get(r)){
                aux.add(arr.get(l));
                l++;
            } else {
                aux.add(arr.get(r));
                r++;
            }
        }
        // copy the rest
        while(l<=mid){
            aux.add(arr.get(l));
            l++;
        }
        while(r<=end){
            aux.add(arr.get(r));
            r++;
        }
        // copy aux back
        for(int i=0;i<aux.size();i++){
            arr.set(start + i,aux.get(i));
        }
    }



}
