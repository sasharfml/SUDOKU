//ES234317-Algorithm and Data Structures
//* Semester Ganjil, 2024/2025
//* Group Capstone Project
//* Group #14 Kelas C
//* 1 - 5026231151 - Kayla Nathania Azzahra
// * 2 - 5026231202 - Alisha Rafimalia


package sudoku;

public class Puzzle {
    int[][] numbers = new int[GameBoardPanel.GRID_SIZE][GameBoardPanel.GRID_SIZE];
    boolean[][] isGiven = new boolean[GameBoardPanel.GRID_SIZE][GameBoardPanel.GRID_SIZE];

    public Puzzle() {
        for (int row = 0; row < GameBoardPanel.GRID_SIZE; ++row) {
            for (int col = 0; col < GameBoardPanel.GRID_SIZE; ++col) {
                isGiven[row][col] = true; // Initialize all as given
            }
        }
    }

    public void newPuzzle(int cellsToGuess) {
        int[][] hardcodedNumbers = {
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };

        for (int row = 0; row < GameBoardPanel.GRID_SIZE; ++row) {
            for (int col = 0; col < GameBoardPanel.GRID_SIZE; ++col) {
                numbers[row][col] = hardcodedNumbers[row][col];
                isGiven[row][col] = true; // Reset all to given
            }
        }

        int count = 0;
        while (count < cellsToGuess) {
            int row = (int) (Math.random() * GameBoardPanel.GRID_SIZE);
            int col = (int) (Math.random() * GameBoardPanel.GRID_SIZE);
            if (isGiven[row][col]) {
                isGiven[row][col] = false;
                count++;
            }
        }
    }
}