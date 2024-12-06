//ES234317-Algorithm and Data Structures
//* Semester Ganjil, 2024/2025
//* Group Capstone Project
//* Group #14 Kelas C
//* 1 - 5026231151 - Kayla Nathania Azzahra
// * 2 - 5026231202 - Alisha Rafimalia



package sudoku;




import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sudoku extends JFrame {
    private static final long serialVersionUID = 1L;

    private GameBoardPanel board = new GameBoardPanel(this);
    private WelcomePanel welcomePanel = new WelcomePanel();
    private LevelSelectionPanel levelSelectionPanel = new LevelSelectionPanel();
    private JPanel gamePanel = new JPanel(new BorderLayout());
    private int currentLevel = 1; // Initial level
    private int score = 0; // Initialize score
    private JLabel scoreLabel = new JLabel("Score: 0");

    public Sudoku() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(welcomePanel, BorderLayout.CENTER);

        // Set the preferred size to maintain a 1:1 aspect ratio
        int size = 600; // You can adjust this value to make the window larger or smaller
        setPreferredSize(new Dimension(size, size));

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setVisible(true);
        setLocationRelativeTo(null); // Center the window on the screen
    }

    private class WelcomePanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private Image backgroundImage;

        public WelcomePanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            // Load the background image
            backgroundImage = new ImageIcon("src/sudoku/final.jpeg").getImage();

            // Create a label for the welcome text
            JLabel welcomeLabel = new JLabel("WELCOME", JLabel.CENTER);
            welcomeLabel.setFont(new Font("Poppins", Font.BOLD, 48)); // Larger font size
            welcomeLabel.setForeground(Color.WHITE); // Set text color to white for contrast
            welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton startButton = new JButton("Start Game");
            startButton.setFont(new Font("Poppins", Font.BOLD, 20)); // Larger font size for button
            startButton.setForeground(new Color(105, 105, 105)); // Dark gray color
            startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startGame();
                }
            });

            JButton chooseLevelButton = new JButton("Choose Your Level");
            chooseLevelButton.setFont(new Font("Poppins", Font.BOLD, 20)); // Larger font size for button
            chooseLevelButton.setForeground(new Color(105, 105, 105)); // Dark gray color
            chooseLevelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            chooseLevelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showLevelSelection();
                }
            });

            add(Box.createVerticalGlue());
            add(welcomeLabel);
            add(Box.createRigidArea(new Dimension(0, 20)));
            add(startButton);
            add(Box.createRigidArea(new Dimension(0, 20)));
            add(chooseLevelButton);
            add(Box.createVerticalGlue());

            // Play the sound when the welcome panel is created
            playSound("src/sudoku/chillguy.wav");
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private class LevelSelectionPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private Image backgroundImage;

        public LevelSelectionPanel() {
            setLayout(new BorderLayout());

            // Load the background image
            backgroundImage = new ImageIcon("src/sudoku/final.jpeg").getImage();

            // Create a "Back" button
            JButton backButton = new JButton("Back");
            backButton.setFont(new Font("Poppins", Font.BOLD, 14));
            backButton.setOpaque(false);
            backButton.setContentAreaFilled(false);
            backButton.setBorderPainted(false);
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    returnToWelcome(); // Return to the welcome screen
                }
            });

            // Create a panel for the top section
            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setOpaque(false);

            // Add the "Back" button to the top-left corner
            topPanel.add(backButton, BorderLayout.WEST);

            // Add a label for the title
            JLabel titleLabel = new JLabel("Choose Your Level", JLabel.CENTER);
            titleLabel.setFont(new Font("Poppins", Font.BOLD, 32));
            titleLabel.setForeground(new Color(76, 0, 153)); // Dark purple with low saturation

            // Create a panel to hold the title label and center it
            JPanel titlePanel = new JPanel();
            titlePanel.setOpaque(false); // Make the panel transparent
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label
            titlePanel.add(Box.createVerticalGlue()); // Add vertical glue for centering
            titlePanel.add(titleLabel);
            titlePanel.add(Box.createVerticalGlue()); // Add vertical glue for centering

            // Add the title panel to the center of the top panel
            topPanel.add(titlePanel, BorderLayout.CENTER);

            // Add the top panel to the north of the main panel
            add(topPanel, BorderLayout.NORTH);

            // Create a panel for the level buttons with increased spacing
            JPanel buttonPanel = new JPanel(new GridLayout(2, 5, 20, 20)); // Increase gaps to 20 pixels
            buttonPanel.setOpaque(false); // Make the panel transparent
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the panel

            for (int i = 1; i <= 10; i++) {
                JPanel levelPanel = new JPanel();
                levelPanel.setPreferredSize(new Dimension(60, 20)); // Reduce height for the buttons
                levelPanel.setBackground(new Color(221, 160, 221)); // Light purple color
                levelPanel.setLayout(new BorderLayout());

                JButton levelButton = new JButton("Level " + i);
                levelButton.setFont(new Font("Poppins", Font.BOLD, 14)); // Use Poppins font with smaller size
                levelButton.setForeground(new Color(105, 105, 105)); // Dark gray color for text
                levelButton.setContentAreaFilled(false); // Make button transparent to show background
                final int level = i;
                levelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentLevel = level;
                        startGame();
                    }
                });

                levelPanel.add(levelButton, BorderLayout.CENTER);
                buttonPanel.add(levelPanel);
            }

            // Add the button panel to the center
            add(buttonPanel, BorderLayout.CENTER);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Draw image fullscreen
        }
    }
    private void showLevelSelection() {
        getContentPane().removeAll();
        getContentPane().add(levelSelectionPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    public void playSound(String soundFilePath) {
        try {
            File soundFile = new File(soundFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Play the sound once
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void startGame() {
        System.out.println("Starting new game at level: " + currentLevel);
        getContentPane().removeAll();
        gamePanel.removeAll();
        gamePanel.add(board, BorderLayout.CENTER);

        // Create a panel for the score label and buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Update the score label's font
        scoreLabel.setFont(new Font("Poppins", Font.BOLD, 24)); // Larger and bold font

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        JButton resetButton = new JButton("Reset");
        JButton hintButton = new JButton("Hint");
        JButton nextLevelButton = new JButton("Next Level");
        JButton quitButton = new JButton("Quit");

        // Add action listeners for the buttons
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.newGame(currentLevel * 5); // Reset the current level
                resetScore(); // Reset score when starting a new game
            }
        });

        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.useHint(); // Use hint
            }
        });

        nextLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentLevel++;
                startGame(); // Start the next level
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToWelcome(); // Return to the welcome screen
            }
        });

        // Add buttons to the button panel
        buttonPanel.add(resetButton);
        buttonPanel.add(hintButton); // Add hint button here
        buttonPanel.add(nextLevelButton);
        buttonPanel.add(quitButton);

        // Add the score label and button panel to the bottom panel
        bottomPanel.add(scoreLabel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        // Add the bottom panel to the game panel
        gamePanel.add(bottomPanel, BorderLayout.SOUTH);

        getContentPane().add(gamePanel, BorderLayout.CENTER);
        board.newGame(currentLevel * 5); // Pass the difficulty level
        revalidate();
        repaint();
    }
    public void updateScore(boolean correct) {
        if (correct) {
            score += 10; // Increase score for correct answer
        } else {
            score = Math.max(0, score - 3); // Decrease score for incorrect answer, but not below 0
        }
        scoreLabel.setText("Score: " + score);
    }

    private void resetScore() {
        score = 0;
        scoreLabel.setText("Score: 0");
    }

    public int getScore() {
        return score;
    }

    public void returnToWelcome() {
        currentLevel = 1; // Reset to initial level
        getContentPane().removeAll();
        getContentPane().add(welcomePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    public Sudoku getSudoku() {
        return (Sudoku) SwingUtilities.getWindowAncestor(this);
    }
    public void startNextLevel() {
        currentLevel++;
        startGame(); // Start the next level with increased difficulty
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Sudoku());
    }
}
// Other methods remain unchanged...