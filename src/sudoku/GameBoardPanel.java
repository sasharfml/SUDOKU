//ES234317-Algorithm and Data Structures
//* Semester Ganjil, 2024/2025
//* Group Capstone Project
//* Group #14 Kelas C
//* 1 - 5026231151 - Kayla Nathania Azzahra
// * 2 - 5026231202 - Alisha Rafimalia


package sudoku;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

                // Change the border color to purple
                cells[row][col].setBorder(BorderFactory.createMatteBorder(
                        top, left, bottom, right, new Color(128, 0, 128))); // Purple color

                gridPanel.add(cells[row][col]);
            }
        }

        setBorder(BorderFactory.createLineBorder(new Color(128, 0, 128), 2)); // Purple color for outer border
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
                                sudoku.updateScore(true); // Correct answer
                                cells[row][col].setAnsweredCorrectly(true);
                            }
                        } else {
                            allCorrect = false;
                            if (!cells[row][col].isAnsweredCorrectly()) {
                                sudoku.updateScore(false); // Incorrect answer
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

    public void useHint() {
        if (sudoku.getScore() < 10) {
            JOptionPane.showMessageDialog(this, "You need at least 10 Points to Use this Hint", "Insufficient Points", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Cell> emptyCells = new ArrayList<>();
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                if (cells[row][col].getTextField().isEditable() && cells[row][col].getTextField().getText().isEmpty()) {
                    emptyCells.add(cells[row][col]);
                }
            }
        }

        if (!emptyCells.isEmpty()) {
            Random random = new Random();
            Cell randomCell = emptyCells.get(random.nextInt(emptyCells.size()));
            int correctNumber = puzzle.numbers[randomCell.getRow()][randomCell.getCol()];
            randomCell.getTextField().setText(String.valueOf(correctNumber));
            randomCell.getTextField().setEditable(false);
            randomCell.setAnsweredCorrectly(true);
            randomCell.getTextField().setBackground(new Color(144, 238, 144)); // Set background to green
            sudoku.updateScore(false); // Deduct 10 points

            // Manually check if all cells are correctly filled
            boolean allCorrect = true;
            for (int row = 0; row < GRID_SIZE; ++row) {
                for (int col = 0; col < GRID_SIZE; ++col) {
                    JTextField textField = cells[row][col].getTextField();
                    if (textField.isEditable()) {
                        if (!textField.getText().equals(String.valueOf(puzzle.numbers[row][col]))) {
                            allCorrect = false;
                            break;
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

    private void checkAllCellsCorrect() {
        boolean allCorrect = true;
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                if (!cells[row][col].isAnsweredCorrectly()) {
                    allCorrect = false;
                    break;
                }
            }
        }

        if (allCorrect) {
            JOptionPane.showMessageDialog(this, "Congratulations! You have completed the puzzle!", "Puzzle Complete", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}