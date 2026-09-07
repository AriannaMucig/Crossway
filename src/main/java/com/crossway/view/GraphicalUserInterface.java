package com.crossway.view;

import com.crossway.model.Board;
import com.crossway.model.PlayerColor;
import com.crossway.model.Position;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Optional;

public class GraphicalUserInterface extends JFrame implements GameView {

    private static final int ROW = 19;
    private static final int COLUMN = 19;
    private final JButton[][] grid = new JButton[ROW][COLUMN];
    private final JLabel label;
    private Position lastClickedPosition = null;
    private boolean restartRequested = false;

    public GraphicalUserInterface() {
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
        infoButton.addActionListener(e -> printRules());

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

        label = new JLabel("Welcome to Crossway");
        label.setForeground(Color.BLACK);
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 28));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setOpaque(false);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        centerPanel.setOpaque(false);
        centerPanel.add(label);
        topPanel.add(centerPanel, BorderLayout.CENTER);

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

                button.addActionListener(e -> onSquareClicked(currentRow, currentColumn));

                grid[r][c] = button;
                gridPanel.add(button);
            }
        }

        Border coloredBorder = getBorder();
        gridPanel.setBorder(coloredBorder);
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
        setVisible(true);
    }

    private void onSquareClicked(int r, int c) {
        synchronized (this) {
            this.lastClickedPosition = new Position(r, c);
            this.notifyAll();
        }
    }

    private synchronized void handleRestartButton() {
        int confirmRestart = JOptionPane.showConfirmDialog(
                this,
                "Do you want to restart the game?",
                "Restart",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirmRestart == JOptionPane.YES_OPTION) {
            this.restartRequested = true;
            this.notifyAll();
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

    @Override
    public void printRules() {
        String rules = """
                CROSSWAY RULES:
                - White attempts to form a continuous chain connecting the North and South borders.
                - Black attempts to form a continuous chain connecting the West and East borders.
                - Chains can connect orthogonally (up/down/left/right) or diagonally.
                - Crossway Constraint: A player is forbidden from placing a piece that completes a 2x2 square of alternating pieces (W-B / B-W), as this creates an illegal diagonal intersection.
                - Pie Rule: After Black makes the very first move, White has the option to swap colors and adopt Black's position.
                """;
        JOptionPane.showMessageDialog(this, rules, "Rules", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void printBoard(Board board) {
        SwingUtilities.invokeLater(() -> {
            for (int r = 0; r < ROW; r++) {
                for (int c = 0; c < COLUMN; c++) {
                    Position pos = new Position(r, c);
                    Optional<PlayerColor> occupant = board.getStone(pos);
                    if (occupant.isPresent()) {
                        grid[r][c].setBackground(occupant.get() == PlayerColor.BLACK ? Color.BLACK : Color.WHITE);
                        grid[r][c].setEnabled(false);
                    } else {
                        grid[r][c].setBackground(new Color(224, 210, 239));
                        grid[r][c].setEnabled(true);
                    }
                }
            }
        });
    }

    @Override
    public boolean askPieRule() {
        Object[] options = {"Yes", "No"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Do you want to apply the Pie Rule?",
                "Pie Rule",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[1]
        );
        return choice == JOptionPane.YES_OPTION;
    }

    @Override
    public synchronized Position askForMove(PlayerColor playerColor) {
        label.setText("Turn of " + playerColor);
        label.setForeground(playerColor == PlayerColor.BLACK ? Color.BLACK : Color.WHITE);

        lastClickedPosition = null;

        while (lastClickedPosition == null && !restartRequested) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (restartRequested) {
            return null;
        }

        return lastClickedPosition;
    }

    @Override
    public void printMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void printError(String error) {
        JOptionPane.showMessageDialog(this, error, "Invalid Move", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public synchronized boolean isRestartRequested() {
        if (restartRequested) {
            restartRequested = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean askPlayAgain(PlayerColor winner) {
        Object[] options = {"Restart", "Exit"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "The winner is " + winner + "!\nDo you want to play again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice != JOptionPane.YES_OPTION) {
            dispose();
            return false;
        }
        return true;
    }

    @Override
    public void resetView() {
        SwingUtilities.invokeLater(() -> {
            for (int r = 0; r < ROW; r++) {
                for (int c = 0; c < COLUMN; c++) {
                    grid[r][c].setBackground(new Color(224, 210, 239));
                    grid[r][c].setEnabled(true);
                }
            }
            label.setText("Welcome to Crossway");
            label.setForeground(Color.BLACK);
        });
    }
}