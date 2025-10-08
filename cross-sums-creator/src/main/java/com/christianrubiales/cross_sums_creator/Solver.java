package com.christianrubiales.cross_sums_creator;

import java.util.Arrays;

public class Solver {

    static void solve(Board board) {
        char[][] grid = copy(board);
    }

    static char[][] copy(Board board) {
        char[][] grid = Arrays.copyOf(board.initialGrid, board.initialGrid.length);
        for (int i = 0; i < board.initialGrid.length; i++) {
            grid[i] = Arrays.copyOf(board.initialGrid[i], board.initialGrid[i].length);
        }

        return grid;
    }

    static boolean isWin(Board board, char[][] grid) {
        for (int i = 0; i < board.size; i++) {
            for (int j = 0; j < board.size; j++) {
                if (grid[i][j] != board.solvedGrid[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    static void removeInColumnsCellsGreaterThanSum() {

    }

    static void removeInRowsCellsGreaterThanSum() {

    }

    static void removeNonContributingCellsInColumn(Board board) {

    }
}
