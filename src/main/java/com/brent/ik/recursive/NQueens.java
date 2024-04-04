package com.brent.ik.recursive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NQueens {

    private static final char queen = 'q';
    private static final char no_queen = '-';
    static List<List<String>> nQueens(Integer n) {
        // the index is the row, the value is the column
        List<List<Integer>> solution = new ArrayList<>();
        ArrayList<Integer> partialSolution = new ArrayList<>();
        // initialize the solutions to empty
        for(int i=0;i<n;i++){
            partialSolution.add(-1);
        }
        nQueens(n,0,partialSolution,solution);
        var answer = printout(n, solution);
        return answer;
    }

    private static List<List<String>> printout(Integer n, List<List<Integer>> solution) {
        var answer = solution.stream().map(sol->{
            List<String> checkerboard = new ArrayList<>();
            for (int row = 0; row < n; row++) {
                int column = sol.get(row);
                String rowString = "";
                for(int col = 0; col< n; col++){
                    if(col==column){
                        rowString += queen;
                    } else rowString += no_queen;
                }
                checkerboard.add(rowString);
            }
            return checkerboard;
        }).toList();
        return answer;
    }

    private static void nQueens(Integer n, int row, List<Integer> partialSolution, List<List<Integer>> solution) {
        var solution1 = new ArrayList<Integer>();
        solution1.add(0,2);
        solution1.add(1,0);
        solution1.add(2,3);
        solution1.add(3,1);
        solution.add(solution1);
        var solution2 = new ArrayList<Integer>();
        solution2.add(0,1);
        solution2.add(1,3);
        solution2.add(2,0);
        solution2.add(3,2);
        solution.add(solution2);
//        Arrays.asList(
//                Arrays.asList("--q-",
//                        "q---",
//                        "---q",
//                        "-q--")
//
//                , Arrays.asList("-q--",
//                        "---q",
//                        "q---",
//                        "--q-")
//        );

    }

}
