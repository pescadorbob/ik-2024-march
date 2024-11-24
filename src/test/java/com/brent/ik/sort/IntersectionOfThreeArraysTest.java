package com.brent.ik.sort;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class IntersectionOfThreeArraysTest {
    @Test
    void testIntersection() throws IOException {
        var threeArrays = loadThreeArrays("input016.json");
        var expectedIntersection = loadSingleArray("output016.json");
        var actual = intersection(threeArrays.arr1,threeArrays.arr2,threeArrays.arr3);
        assertThat(actual).isEqualTo(expectedIntersection);
    }

    private ArrayList<Integer> loadSingleArray(String fileName) throws IOException {
        var inputJsonStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        var arr = new ObjectMapper().readValue(inputJsonStream,int[].class);
        var list = new ArrayList<Integer>();
        for (int j : arr) {
            list.add(j);
        }
        return list;
    }

    public static ArrayList<Integer> intersection(ArrayList<Integer> arr1, ArrayList<Integer> arr2, ArrayList<Integer> arr3){
        // get the result to return
        var result = new ArrayList<Integer>();

        // indexes for each of the arrays
        int i=0,j=0,k=0;

        // sizes of the arrays
        int M=arr1.size(), N=arr2.size(), O=arr3.size();

        // iterate through the arrays as long as each of the indexes is within bounds of its array. As
        // soon as it goes out of bounds of its array, it's done as an intersection can no longer exist.
        while(i<M & j<N & k<O){

            // add a value if it is an intersection
            if(arr1.get(i).equals(arr2.get(j)) && arr2.get(j).equals(arr3.get(k))){
                result.add(arr1.get(i));
            }

            // find the smallest value
            int mini = Math.min(arr1.get(i),Math.min(arr2.get(j),arr3.get(k)));

            //inch forward
            if(arr1.get(i) == mini){
                i++;
            }
            if(arr2.get(j) == mini){
                j++;
            }
            if(arr3.get(k) == mini){
                k++;
            }

        }
        // if it is empty, add -1 to signal it is empty
        if(result.size()==0) result.add(-1);

        return result;
    }


    private ThreeArrays loadThreeArrays(String threeArraysJsonFileName) throws IOException {
        var inputJsonStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(threeArraysJsonFileName);

        var result = new ObjectMapper().readValue(inputJsonStream,ThreeArrays.class);
        return result;
    }


    public static class ThreeArrays {
        ArrayList<Integer> arr1;
        ArrayList<Integer> arr2;
        ArrayList<Integer> arr3;

        public ArrayList<Integer> getArr1() {
            return arr1;
        }

        public void setArr1(ArrayList<Integer> arr1) {
            this.arr1 = arr1;
        }

        public ArrayList<Integer> getArr2() {
            return arr2;
        }

        public void setArr2(ArrayList<Integer> arr2) {
            this.arr2 = arr2;
        }

        public ArrayList<Integer> getArr3() {
            return arr3;
        }

        public void setArr3(ArrayList<Integer> arr3) {
            this.arr3 = arr3;
        }
    }


}
