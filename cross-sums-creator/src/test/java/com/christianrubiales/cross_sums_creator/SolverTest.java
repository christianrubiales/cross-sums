package com.christianrubiales.cross_sums_creator;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SolverTest {

    Board getBoard(int size) {
        Board board = null;

        while (board == null) {
            try {
                board = new Board(size);
            } catch (Exception e) {
                // empty
            }
        }

        return board;
    }

    @Test
    void copy() {
        char[][] initialGrid = {
                {'1', '1', '7', '9'},
                {'8', '1', '2', '4'},
                {'9', '2', '8', '3'},
                {'4', '1', '9', '1'}
        };
        Board board = getBoard(4);
        board.initialGrid = initialGrid;

        char[][] copy = Solver.copy(board);

        for (int i = 0; i < 4; i++) {
            assertArrayEquals(initialGrid[i], copy[i]);
        }
    }

    @Test
    void isWin() {
        Board board = getBoard(4);
        board.initialGrid = board.solvedGrid;
        char[][] grid = Solver.copy(board);
        assertTrue(Solver.isWin(board, grid));
    }
}
