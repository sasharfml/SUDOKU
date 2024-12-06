//ES234317-Algorithm and Data Structures
//* Semester Ganjil, 2024/2025
//* Group Capstone Project
//* Group #14 Kelas C
//* 1 - 5026231151 - Kayla Nathania Azzahra
// * 2 - 5026231202 - Alisha Rafimalia

package sudoku;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;



public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    public static final int GRID_SIZE = 9;
    public static final int SUBGRID_SIZE = 3;
    private Cell[][] cells = new Cell[GRID_SIZE][GRID_SIZE];
    private Puzzle puzzle = new Puzzle();
    private Sudoku sudoku; // Reference to the Sudoku instance

    public GameBoardPanel(Sudoku sudoku) {
        super.setLayout(new BorderLayout());
        this.sudoku = sudoku; // Initialize the Sudoku instance

        JPanel gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE, 0, 0));
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col, this);

                int top = (row % SUBGRID_SIZE == 0) ? 2 : 1;
                int left = (col % SUBGRID_SIZE == 0) ? 2 : 1;
                int bottom = (row % SUBGRID_SIZE == SUBGRID_SIZE - 1) ? 2 : 1;
                int right = (col % SUBGRID_SIZE == SUBGRID_SIZE - 1) ? 2 : 1;

                cells[row][col].setBorder(BorderFactory.createMatteBorder(
                        top, left, bottom, right, Color.BLACK));

                gridPanel.add(cells[row][col]);
            }
        }

        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(gridPanel, BorderLayout.CENTER);
    }

    public void newGame(int difficultyLevel) {
        puzzle.newPuzzle(difficultyLevel);
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
    }

    public void checkAnswers() {
        boolean allCorrect = true;

        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                JTextField textField = cells[row][col].getTextField();

                if (textField.isEditable()) {
                    if (!textField.getText().isEmpty()) {
                        boolean isCorrect = cells[row][col].checkAnswer(puzzle.numbers[row][col]);

                        if (isCorrect) {
                            if (!cells[row][col].isAnsweredCorrectly()) {
                                sudoku.updateScore(true);
                                cells[row][col].setAnsweredCorrectly(true);
                            }
                        } else {
                            allCorrect = false;
                            if (cells[row][col].isAnsweredCorrectly()) {
                                sudoku.updateScore(false);
                                cells[row][col].setAnsweredCorrectly(false);
                            }
                        }
                    } else {
                        allCorrect = false;
                    }
                }
            }
        }

        if (allCorrect) {
            sudoku.playSound("src/sudoku/menang.wav"); // Play victory sound once

            int option = JOptionPane.showOptionDialog(
                    this,
                    "Congratulations! You have finished this level!",
                    "Level Complete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[]{"Next Level", "Quit"},
                    "Next Level"
            );

            if (option == JOptionPane.YES_OPTION) {
                sudoku.startNextLevel(); // Start the next level
            } else if (option == JOptionPane.NO_OPTION) {
                sudoku.returnToWelcome(); // Return to the welcome screen
            }
        }
    }
}