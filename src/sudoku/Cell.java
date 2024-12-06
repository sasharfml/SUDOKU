//ES234317-Algorithm and Data Structures
//* Semester Ganjil, 2024/2025
//* Group Capstone Project
//* Group #14 Kelas C
//* 1 - 5026231151 - Kayla Nathania Azzahra
// * 2 - 5026231202 - Alisha Rafimalia



package sudoku;



import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Cell extends JPanel {
    private static final long serialVersionUID = 1L;
    private int row, col;
    private JTextField textField;
    private GameBoardPanel gameBoardPanel;
    private boolean answeredCorrectly = false; // Track if the cell is answered correctly

    public Cell(int row, int col, GameBoardPanel gameBoardPanel) {
        this.row = row;
        this.col = col;
        this.gameBoardPanel = gameBoardPanel;
        this.textField = new JTextField("", SwingConstants.CENTER);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font("Arial", Font.BOLD, 18));
        setLayout(new BorderLayout());
        add(textField, BorderLayout.CENTER);
        setPreferredSize(new Dimension(50, 50));

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = textField.getText();
                if (!text.matches("[1-9]")) {
                    textField.setText(""); // Clear invalid input
                } else {
                    gameBoardPanel.checkAnswers(); // Check answers after input
                }
            }
        });
    }

    public void newGame(int number, boolean isGiven) {
        if (isGiven) {
            textField.setText(String.valueOf(number));
            textField.setForeground(Color.BLACK);
            textField.setEditable(false);
            textField.setBackground(Color.WHITE);
        } else {
            textField.setText("");
            textField.setEditable(true);
            textField.setBackground(new Color(230, 200, 250));
        }
        answeredCorrectly = false; // Reset the answered state for new games
    }

    public boolean checkAnswer(int correctNumber) {
        if (!textField.isEditable() || textField.getText().isEmpty()) return true;

        String text = textField.getText();
        if (text.equals(String.valueOf(correctNumber))) {
            textField.setBackground(new Color(144, 238, 144));
            return true;
        } else {
            textField.setBackground(new Color(255, 182, 193));
            return false;
        }
    }

    public JTextField getTextField() {
        return textField;
    }

    public boolean isAnsweredCorrectly() {
        return answeredCorrectly;
    }

    public void setAnsweredCorrectly(boolean answeredCorrectly) {
        this.answeredCorrectly = answeredCorrectly;
    }

    // Add these getter methods
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}