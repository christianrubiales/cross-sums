package com.christianrubiales.cross_sums_creator;

import java.util.*;

public class Solver {

    static List<Step> solve(Board board) {
        char[][] grid = copy(board.initialGrid);
        boolean[][] solved = new boolean[board.size][board.size];

        List<Step> steps = new ArrayList<>();
        steps.addAll(removeInColumnsCellsGreaterThanSum(board, grid, solved));
        steps.addAll(removeInRowsCellsGreaterThanSum(board, grid, solved));

        while (!isWin(board, grid)) {
            char[][] start = copy(grid);
            steps.addAll(removeNonContributingCellsInColumns(board, grid, solved));
            if (isWin(board, grid)) break;
            steps.addAll(removeNonContributingCellsInRows(board, grid, solved));
            if (isWin(board, grid)) break;
            steps.addAll(removeCellsInSolvedColumns(board, grid, solved));
            if (isWin(board, grid)) break;
            char[][] end = copy(grid);
            if (equals(start, end)) {
                throw new IllegalStateException("Solver did not solve the grid:\nsolved:\n" + toJsonString(solved)
                        + "grid:\n" + Board.toJsonString(grid)
                        + "\nrowSums: " + Arrays.toString(board.rowSums)
                        + "\ncolSums: " + Arrays.toString(board.colSums));
            }
        }

        return steps;
    }

    static char[][] copy(char[][] original) {
        char[][] grid = Arrays.copyOf(original, original.length);
        for (int i = 0; i < original.length; i++) {
            grid[i] = Arrays.copyOf(original[i], original[i].length);
        }

        return grid;
    }

    static boolean equals(char[][] grid1, char[][] grid2) {
        for (int i = 0; i < grid1.length; i++) {
            for (int j = 0; j < grid1.length; j++) {
                if (grid1[i][j] != grid2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean isWin(Board board, char[][] grid) {
        return equals(board.solvedGrid, grid);
    }

    static int i(char c) {
        return (int) c - '0';
    }

    static String toJsonString(boolean[][] grid) {
        StringBuilder builder = new StringBuilder();

        builder.append("    [\n");
        for (int i = 0; i < grid.length; i++) {
            builder.append("      [");
            for (int j = 0; j < grid.length; j++) {
                builder.append("\"" + grid[i][j] + "\"");
                if (j != grid.length - 1)  builder.append(", ");
            }
            builder.append("]");
            if (i < grid.length-1) builder.append(",");
            builder.append("\n");
        }
        builder.append("    ],\n");

        return builder.toString();
    }


    static List<Step> removeInColumnsCellsGreaterThanSum(Board board, char[][] grid, boolean[][] solved) {
        List<Step> steps = new ArrayList<>();
        String reason = "Remove cells greater than sum in columns";

        for (int j = 1; j <= board.size; j++) {
            for (int i = 1; i <= board.size; i++) {
                if (grid[i-1][j-1] != ' ' && i(grid[i-1][j-1]) > board.colSums[j-1]) {
                    grid[i-1][j-1] = ' ';
                    steps.add(new Step(i, j, reason));
                    solved[i-1][j-1] = true;
                }
            }
        }

        return steps;
    }

    static void markSolved() {
        // TODO
    }

    static List<Step> removeInRowsCellsGreaterThanSum(Board board, char[][] grid, boolean[][] solved) {
        List<Step> steps = new ArrayList<>();
        String reason = "Remove cells greater than sum in rows";

        for (int i = 1; i <= board.size; i++) {
            for (int j = 1; j <= board.size; j++) {
                if (grid[i-1][j-1] != ' ' && i(grid[i-1][j-1]) > board.rowSums[i-1]) {
                    grid[i-1][j-1] = ' ';
                    steps.add(new Step(i, j, reason));
                    solved[i-1][j-1] = true;
                }
            }
        }

        return steps;
    }

    static List<Step> removeNonContributingCellsInColumns(Board board, char[][] grid, boolean[][] solved) {
        List<Step> steps = new ArrayList<>();
        String reason = "Remove column cells that will not be usable to create the sum";

        for (int j = 1; j <= board.size; j++) {
            List<Integer> numbers = new ArrayList<>();
            Set<Integer> numberSet = new HashSet<>();

            // collect numbers in column
            for (int i = 1; i <= board.size; i++) {
                if (grid[i-1][j-1] != ' ') {
                    numbers.add(i(grid[i-1][j-1]));
                    numberSet.add(i(grid[i-1][j-1]));
                }
            }

            // collect contributing numbers
            Set<Integer> contributingNumbers = new HashSet<>();
            for (int k = 1; k < Math.pow(2, numbers.size()); k++) {
                BitSet bitset = BitSet.valueOf(new long[]{k});
                int sum = 0;
                for (int index = bitset.nextSetBit(0); index >= 0; index = bitset.nextSetBit(index+1)) {
                    sum += numbers.get(bitset.nextSetBit(index));
                    if (sum > board.colSums[j-1]) {
                        break;
                    }
                }
                if (sum == board.colSums[j-1]) {
                    for (int index = bitset.nextSetBit(0); index >= 0; index = bitset.nextSetBit(index+1)) {
                        sum += numbers.get(bitset.nextSetBit(index));
                        contributingNumbers.add(numbers.get(bitset.nextSetBit(index)));
                    }
                }
            }

            // remove non-contributing numbers
            numberSet.removeAll(contributingNumbers);
            for (int i = 1; i <= board.size; i++) {
                if (numberSet.contains(i(grid[i-1][j-1]))) {
                    steps.add(new Step(i, j, reason));
                    solved[i-1][j-1] = true;
                }
            }
        }

        return steps;
    }

    static List<Step> removeNonContributingCellsInRows(Board board, char[][] grid, boolean[][] solved) {
        List<Step> steps = new ArrayList<>();
        String reason = "Remove row cells that will not be usable to create the sum";

        for (int i = 1; i <= board.size; i++) {
            List<Integer> numbers = new ArrayList<>();
            Set<Integer> numberSet = new HashSet<>();

            // collect numbers in row
            for (int j = 1; j <= board.size; j++) {
                if (grid[i-1][j-1] != ' ') {
                    numbers.add(i(grid[i-1][j-1]));
                    numberSet.add(i(grid[i-1][j-1]));
                }
            }

            // collect contributing numbers
            Set<Integer> contributingNumbers = new HashSet<>();
            for (int k = 1; k < Math.pow(2, numbers.size()); k++) {
                BitSet bitset = BitSet.valueOf(new long[]{k});
                int sum = 0;
                for (int index = bitset.nextSetBit(0); index >= 0; index = bitset.nextSetBit(index+1)) {
                    sum += numbers.get(bitset.nextSetBit(index));
                    if (sum > board.rowSums[i-1]) {
                        break;
                    }
                }
                if (sum == board.rowSums[i-1]) {
                    for (int index = bitset.nextSetBit(0); index >= 0; index = bitset.nextSetBit(index+1)) {
                        sum += numbers.get(bitset.nextSetBit(index));
                        contributingNumbers.add(numbers.get(bitset.nextSetBit(index)));
                    }
                }
            }

            // remove non-contributing numbers
            numberSet.removeAll(contributingNumbers);
            for (int j = 1; j <= board.size; j++) {
                if (numberSet.contains(i(grid[i-1][j-1]))) {
                    steps.add(new Step(i, j, reason));
                    solved[i-1][j-1] = true;
                }
            }
        }

        return steps;
    }

    static List<Step> removeCellsInSolvedColumns(Board board, char[][] grid, boolean[][] solved) {
        List<Step> steps = new ArrayList<>();
        String reason = "Remove unneeded cells in solved columns";

        for (int j = 1; j <= board.size; j++) {
            int sum = 0;
            for (int i = 1; i <= board.size; i++) {
                if (solved[i-1][j-1]) {
                    sum += grid[i-1][j-1];
                }
            }
            if (board.colSums[j-1] == sum) {
                for (int i = 1; i <= board.size; i++) {
                    if (!solved[i-1][j-1]) {
                        grid[i-1][j-1] = ' ';
                        steps.add(new Step(i, j, reason));
                    }
                }
            }
        }


        return steps;
    }
}
