package com.brent.ik.recursive;

import java.util.ArrayList;
import java.util.List;

public class NQueens {

    private static final char queen = 'q';
    private static final char no_queen = '-';
    static boolean[] col_occupied;
    static boolean[] slash_diagonal;
    static boolean[] backslash_diagonal;

    static ArrayList<List<String>> nQueens(Integer n) {
        col_occupied = new boolean[n * n];
        slash_diagonal = new boolean[n * n];
        backslash_diagonal = new boolean[n * n];

        // the index is the row, the value is the column
        List<List<Integer>> solution = new ArrayList<>();
        ArrayList<Integer> partialSolution = new ArrayList<>();
        // initialize the solutions to empty
        for (int i = 0; i < n * n; i++) {
            partialSolution.add(-1);
            col_occupied[i] = false;
            slash_diagonal[i] = false;
            backslash_diagonal[i] = false;
        }
        nQueens(n, 0, partialSolution, solution);
        var answer = printout(n, solution);
        return answer;
    }

    private static ArrayList<List<String>> printout(Integer n, List<List<Integer>> solution) {
        var answer = solution.stream().map(sol -> {
            List<String> checkerboard = new ArrayList<>();
            for (int row = 0; row < n; row++) {
                int column = sol.get(row);
                String rowString = "";
                for (int col = 0; col < n; col++) {
                    if (col == column) {
                        rowString += queen;
                    } else rowString += no_queen;
                }
                checkerboard.add(rowString);
            }
            return checkerboard;
        }).toList();
        return new ArrayList<>(answer);
    }

    private static void nQueens(Integer n, int row, List<Integer> partialSolution, List<List<Integer>> solution) {

        if (row == n) {
            // base case, we have a solution!
            solution.add(new ArrayList<>(partialSolution));
            return;
        }
        for (int column = 0; column < n; column++) {
            if (is_safe(row, column, n)) {
                placeQueen(row, column, n, partialSolution);

                nQueens(n, row + 1, partialSolution, solution);

                removeQueen(row, column, n, partialSolution);
            }
        }

    }

    private static void removeQueen(int row, int column, Integer n, List<Integer> partialSolution) {
        col_occupied[column] = false;
        slash_diagonal[row + column] = false;
        backslash_diagonal[row - column + n - 1] = false;
        partialSolution.remove(row);

    }

    private static void placeQueen(int row, int column, int n, List<Integer> partialSolution) {
        col_occupied[column] = true;
        slash_diagonal[row + column] = true;
        backslash_diagonal[row - column + n - 1] = true;
        partialSolution.add(row, column);
    }

    private static boolean is_safe(int row, int column, int n) {
        return !col_occupied[column] &&
                !slash_diagonal[row + column] &&
                !backslash_diagonal[row - column + n - 1];
    }


}
