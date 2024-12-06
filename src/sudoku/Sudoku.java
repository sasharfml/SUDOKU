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
            welcomeLabel.setFont(new Font("Arial", Font.BOLD, 48)); // Larger font size
            welcomeLabel.setForeground(Color.WHITE); // Set text color to white for contrast
            welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton startButton = new JButton("Start Game");
            startButton.setFont(new Font("Arial", Font.BOLD, 24)); // Larger font size for button
            startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startGame();
                }
            });

            add(Box.createVerticalGlue());
            add(welcomeLabel);
            add(Box.createRigidArea(new Dimension(0, 20)));
            add(startButton);
            add(Box.createVerticalGlue());

            // Play the sound when the welcome panel is created
            playSound("src/sudoku/chillguy.wav");
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw the background image scaled to fit the panel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void playSound(String soundFilePath) {
        try {
            File soundFile = new File(soundFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the sound indefinitely
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void startGame() {
        System.out.println("Starting new game at level: " + currentLevel);
        getContentPane().removeAll();
        gamePanel.removeAll();
        gamePanel.add(board, BorderLayout.CENTER);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        JButton resetButton = new JButton("Reset");
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
        buttonPanel.add(nextLevelButton);
        buttonPanel.add(quitButton);

        // Add the button panel to the game panel
        gamePanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the score label to the game panel
        gamePanel.add(scoreLabel, BorderLayout.NORTH);

        getContentPane().add(gamePanel, BorderLayout.CENTER);
        board.newGame(currentLevel * 5); // Pass the difficulty level
        revalidate();
        repaint();
    }

    public void updateScore(boolean correct) {
        if (correct) {
            score += 10; // Increase score for correct answer
        } else {
            score = Math.max(0, score - 5); // Decrease score for incorrect answer, but not below 0
        }
        scoreLabel.setText("Score: " + score);
    }

    private void resetScore() {
        score = 0;
        scoreLabel.setText("Score: 0");
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