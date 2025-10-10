package com.christianrubiales.cross_sums_creator;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {

    static char[][] initialGrid = {
            {'1', '1', '7', '9'},
            {'8', '1', '2', '4'},
            {'9', '2', '8', '3'},
            {'4', '1', '9', '1'}
    };

    static char[][] solvedGrid = {
            {'1', ' ', '7', ' '},
            {' ', '1', '2', '4'},
            {' ', '2', ' ', '3'},
            {'4', '1', ' ', '1'}
    };

    static int[] rowSums = {8, 7, 5, 6};

    static int[] colSums = {5, 4, 9, 8};

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
        char[][] copy = Solver.copy(initialGrid);

        for (int i = 0; i < 4; i++) {
            assertArrayEquals(initialGrid[i], copy[i]);
        }
    }

    @Test
    void equals_true() {
        assertTrue(Solver.equals(initialGrid, initialGrid));
    }

    @Test
    void equals_false() {
        assertFalse(Solver.equals(initialGrid, solvedGrid));
    }

    @Test
    void isWin() {
        Board board = getBoard(4);
        board.initialGrid = board.solvedGrid;
        char[][] grid = Solver.copy(board.initialGrid);
        assertTrue(Solver.isWin(board, grid));
    }

    @Test
    void removeInColumnsCellsGreaterThanSum() {
        Board board = getBoard(4);
        board.initialGrid = initialGrid;
        board.colSums = colSums;
        char[][] grid = Solver.copy(board.initialGrid);
        boolean[][] solved = new boolean[board.size][board.size];

        List<Step> steps = Solver.removeInColumnsCellsGreaterThanSum(board, grid, solved);

        assertEquals(2, steps.get(0).row());
        assertEquals(1, steps.get(0).column());
        assertEquals(3, steps.get(1).row());
        assertEquals(1, steps.get(1).column());
        assertEquals(1, steps.get(2).row());
        assertEquals(4, steps.get(2).column());
    }

    @Test
    void removeInRowsCellsGreaterThanSum() {
        Board board = getBoard(4);
        board.initialGrid = initialGrid;
        board.rowSums = rowSums;
        char[][] grid = Solver.copy(board.initialGrid);
        boolean[][] solved = new boolean[board.size][board.size];

        List<Step> steps = Solver.removeInRowsCellsGreaterThanSum(board, grid, solved);

        assertEquals(1, steps.get(0).row());
        assertEquals(4, steps.get(0).column());
        assertEquals(2, steps.get(1).row());
        assertEquals(1, steps.get(1).column());
    }

    @Test
    void removeNonContributingCellsInColumns() {
        Board board = getBoard(4);
        board.initialGrid = initialGrid;
        board.colSums = colSums;
        char[][] grid = Solver.copy(board.initialGrid);
        boolean[][] solved = new boolean[board.size][board.size];

        List<Step> steps = Solver.removeNonContributingCellsInColumns(board, grid, solved);
        System.out.println(steps);

        assertEquals(2, steps.get(0).row());
        assertEquals(1, steps.get(0).column());
        assertEquals(3, steps.get(1).row());
        assertEquals(1, steps.get(1).column());
        assertEquals(3, steps.get(2).row());
        assertEquals(3, steps.get(2).column());
        assertEquals(1, steps.get(3).row());
        assertEquals(4, steps.get(3).column());
    }

    @Test
    void removeNonContributingCellsInRows() {
        Board board = getBoard(4);
        board.initialGrid = initialGrid;
        board.rowSums = rowSums;
        char[][] grid = Solver.copy(board.initialGrid);
        boolean[][] solved = new boolean[board.size][board.size];

        List<Step> steps = Solver.removeNonContributingCellsInRows(board, grid, solved);

        assertEquals(1, steps.get(0).row());
        assertEquals(4, steps.get(0).column());
        assertEquals(2, steps.get(1).row());
        assertEquals(1, steps.get(1).column());
        assertEquals(3, steps.get(2).row());
        assertEquals(1, steps.get(2).column());
        assertEquals(3, steps.get(3).row());
        assertEquals(3, steps.get(3).column());
        assertEquals(4, steps.get(4).row());
        assertEquals(3, steps.get(4).column());
    }

    @Test
    void solve() {
        char[][] initialGrid = {
                {'1', '2', '7', '4'},
                {'4', '3', '2', '9'},
                {'1', '2', '8', '4'},
                {'4', '3', '9', '1'}
        };
        char[][] solvedGrid = {
                {'1', ' ', '7', ' '},
                {' ', '3', '2', ' '},
                {' ', '2', ' ', '4'},
                {'4', '3', ' ', '1'}
        };
        int[] rowSums = {8, 5, 6, 8};
        int[] colSums = {5, 8, 9, 5};

        Board board = getBoard(4);
        board.initialGrid = initialGrid;
        board.solvedGrid = solvedGrid;
        board.rowSums = rowSums;
        board.colSums = colSums;
        char[][] grid = Solver.copy(board.initialGrid);

        List<Step> steps = Solver.solve(board);
        System.out.println(board.toJsonString());
        for (Step step : steps) {
            System.out.println(step);
        }
    }


    @Test
    void solve2() {
        for (int i = 0; i < 10; i++) {
//            System.out.println(i);
            Board board = getBoard(4);
            try {
                Solver.solve(board);
            } catch (Exception e) {
                System.err.println(board.toJsonString());
            }
        }
    }
}
