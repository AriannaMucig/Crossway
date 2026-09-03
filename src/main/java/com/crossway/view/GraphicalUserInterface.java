package com.crossway.view;

import com.crossway.model.Game;
import com.crossway.model.PlayerColor;
import com.crossway.model.Position;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GraphicalUserInterface extends JFrame {

    private static final int ROW = 19;
    private static final int COLUMN = 19;
    private final JButton[][] grid = new JButton[ROW][COLUMN];
    private Game game;
    private final JLabel label;


    public GraphicalUserInterface(Game game) {
        this.game = game;
        setTitle("Crossway");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 650);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(142, 175, 241));
        topPanel.setOpaque(true);

        JButton infoButton = new JButton("\u2139");
        infoButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        infoButton.setFocusable(false);
        infoButton.setForeground(new Color(164, 239, 193));
        infoButton.setMargin(new Insets(0, 0, 0, 0));
        infoButton.setContentAreaFilled(false);
        infoButton.setBorderPainted(false);
        infoButton.addActionListener(e -> showRules());

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        leftPanel.setOpaque(false);
        leftPanel.add(infoButton);
        topPanel.add(leftPanel, BorderLayout.WEST);

        JButton restartButton = new JButton("\uD83D\uDD03");
        restartButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        restartButton.setForeground(new Color(164, 239, 193));
        restartButton.setFocusable(false);
        restartButton.setMargin(new Insets(0, 0, 0, 0));
        restartButton.setContentAreaFilled(false);
        restartButton.setBorderPainted(false);
        restartButton.addActionListener(e -> handleRestartButton());

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        rightPanel.setOpaque(false);
        rightPanel.add(restartButton);
        topPanel.add(rightPanel, BorderLayout.EAST);

        label = new JLabel();
        label.setForeground(Color.BLACK);
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 28));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setOpaque(false);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        centerPanel.setOpaque(false);
        centerPanel.add(label);
        topPanel.add(centerPanel, BorderLayout.CENTER);

        updateLabel();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(ROW, COLUMN, 1, 1));
        gridPanel.setBackground(new Color(142, 175, 241));

        for (int r = 0; r < ROW; r++) {
            for (int c = 0; c < COLUMN; c++) {
                JButton button = new JButton("");
                button.setFont(new Font("Arial", Font.BOLD, 8));
                button.setBackground(new Color(224, 210, 239));

                int currentRow = r;
                int currentColumn = c;

                button.addActionListener(e -> playMove(currentRow, currentColumn));

                grid[r][c] = button;
                gridPanel.add(button);
            }
        }

        Border coloredBorder = getBorder();

        gridPanel.setBorder(coloredBorder);
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private void handleRestartButton() {
        int confirmRestart = JOptionPane.showConfirmDialog(
                this,
                "Do you want to restart  the game?",
                "Restart",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirmRestart == JOptionPane.YES_OPTION) {
            restartGame();
        }

    }

    private static Border getBorder() {
        Border whiteTop = BorderFactory.createMatteBorder(15, 0, 0, 0, Color.WHITE);
        Border whiteBottom = BorderFactory.createMatteBorder(0, 0, 15, 0, Color.WHITE);
        Border blackLeft = BorderFactory.createMatteBorder(0, 15, 0, 0, Color.BLACK);
        Border blackRight = BorderFactory.createMatteBorder(0, 0, 0, 15, Color.BLACK);

        return BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(whiteTop, blackRight),
                BorderFactory.createCompoundBorder(blackLeft, whiteBottom)
        );
    }

    private void showRules() {
        String rules = """
                Rules:
                - White attempts to form a continuous chain connecting the North and South borders.
                - Black attempts to form a continuous chain connecting the West and East borders.
                - Chains can connect orthogonally (up/down/left/right) or diagonally.
                - Crossway Constraint: A player is forbidden from placing a piece that completes a 2x2 square of alternating pieces (W-B / B-W), as this creates an illegal diagonal intersection.
                - Pie Rule: After Black makes the very first move, White has the option to swap colors and adopt Black's position.
                """;

        JOptionPane.showMessageDialog(
                this,
                rules,
                "Rules",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void playMove(int r, int c) {
        JButton selectedButton = grid[r][c];
        PlayerColor playerWhoMoved = game.getCurrentTurn();

        try {
            Position move = new Position(r, c);
            game.playMove(move);

            if (playerWhoMoved == PlayerColor.BLACK) {
                selectedButton.setBackground(Color.BLACK);
            } else {
                selectedButton.setBackground(Color.WHITE);
            }

            selectedButton.setOpaque(true);
            selectedButton.setContentAreaFilled(true);
            selectedButton.setEnabled(false);
            selectedButton.repaint();

            updateLabel();

        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this,
                    "Invalid move, read the rules",
                    "",
                    JOptionPane.WARNING_MESSAGE);
        }

        if (game.getWinner().isPresent()) {
            handleGameOver(playerWhoMoved);
        }

        if (game.getTurnsCount() == 2) {
            askPieRule(selectedButton);

        }
    }

    private void askPieRule(JButton selectedButton) {
        Object[] options = {"Yes", "No"};

        int choice = JOptionPane.showOptionDialog(
                this,
                "Do you want to apply the Pie Rule?",
                "Pie Rule",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]
        );

        if (choice == JOptionPane.YES_OPTION) {
            game.applyPieRule();
            selectedButton.setBackground(Color.WHITE);
        }
        updateLabel();
    }

    private void handleGameOver(PlayerColor playerWhoMoved) {
        Object[] options = {"Restart", "Esc"};

        int choice = JOptionPane.showOptionDialog(
                this,
                "The winner is " + playerWhoMoved + "!\nDo you want to play again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == JOptionPane.YES_OPTION) {
            restartGame();
        } else {
            dispose();
        }
    }

    private void restartGame() {
        this.game = new Game();

        for (int r = 0; r < ROW; r++) {
            for (int c = 0; c < COLUMN; c++) {
                JButton button = grid[r][c];
                button.setBackground(new Color(224, 210, 239));
                button.setEnabled(true);
            }
        }

        updateLabel();
    }

    private void updateLabel() {
        label.setText("Turn of " + game.getCurrentTurn());
        if (game.getCurrentTurn() == PlayerColor.BLACK) {
            label.setForeground(Color.BLACK);
        } else if (game.getCurrentTurn() == PlayerColor.WHITE) {
            label.setForeground(Color.WHITE);
        }
    }
}
