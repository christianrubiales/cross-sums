package com.christianrubiales.cross_sums_creator;

import java.util.concurrent.ThreadLocalRandom;

public class Board {

    int size;

    char[][] initialGrid;
    char[][] solvedGrid;

    int[] rowSums;
    int[] colSums;

    Board(int size) {
        this.size = size;
        rowSums = new int[size];
        colSums = new int[size];
        initialGrid = new char[size][];
        solvedGrid = new char[size][];
        initialize();
    }

    void initialize() {
        populate();
        repopulateEmpty();
        calculateSums();
    }

    void populate() {
        // Add random numbers to cells
        for (int i = 0; i < size; i++) {
            initialGrid[i] = new char[size];
            for (int j = 0; j < size; j++) {
                initialGrid[i][j] = (char) ('0' + ThreadLocalRandom.current().nextInt(1, 10));
            }
        }

        // Delete random cells
        for (int i = 0; i < size; i++) {
            solvedGrid[i] = new char[size];
            for (int j = 0; j < size; j++) {
                boolean retain = ThreadLocalRandom.current().nextFloat() < 0.5;
                solvedGrid[i][j] = retain ? initialGrid[i][j] : ' ';
            }
        }
    }

    void repopulateEmpty() {
        // Bring back some cells if row or column is empty

        // Check rows
        for (int i = 0; i < size; i++) {
            boolean hasNumber = false;
            for (int j = 0; j < size; j++) {
                if (solvedGrid[i][j] != ' ') {
                    hasNumber = true;
                    break;
                }
            }
            if (!hasNumber) {
                int j = ThreadLocalRandom.current().nextInt(0, size);
                solvedGrid[i][j] = initialGrid[i][j];
            }
        }

        // check columns
        for (int i = 0; i < size; i++) {
            boolean hasNumber = false;
            for (int j = 0; j < size; j++) {
                if (solvedGrid[j][i] != ' ') {
                    hasNumber = true;
                    break;
                }
            }
            if (!hasNumber) {
                int j = ThreadLocalRandom.current().nextInt(0, size);
                solvedGrid[j][i] = initialGrid[j][i];
            }
        }
    }

    void calculateSums() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (solvedGrid[i][j] != ' ') {
                    rowSums[i] += solvedGrid[i][j] - '0';
                }
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (solvedGrid[j][i] != ' ') {
                    colSums[i] += solvedGrid[j][i] - '0';
                }
            }
        }
    }

    String toJsonString() {
        StringBuilder builder = new StringBuilder();

        builder.append("{\n");
        builder.append("\t\"initialGrid\":\n");
        builder.append(toJsonString(initialGrid));
        builder.append("\t\"solvedGrid\":\n");
        builder.append(toJsonString(solvedGrid));

        builder.append("\t\"rowSums\": [");
        for (int i = 0; i < size; i++) {
            builder.append(rowSums[i] + ", ");
        }
        builder.append("],\n");

        builder.append("\t\"colSums\": [");
        for (int i = 0; i < size; i++) {
            builder.append(colSums[i] + ", ");
        }
        builder.append("],\n");

        builder.append("}");

        return builder.toString();
    }

    String toJsonString(char[][] grid) {
        StringBuilder builder = new StringBuilder();

        builder.append("\t\t[\n");
        for (int i = 0; i < size; i++) {
            builder.append("\t\t\t[");
            for (int j = 0; j < size; j++) {
                builder.append("'" + grid[i][j] + "'");
                if (j != size - 1)  builder.append(", ");
            }
            builder.append("],\n");
        }
        builder.append("\t\t],\n");

        return builder.toString();
    }
}
