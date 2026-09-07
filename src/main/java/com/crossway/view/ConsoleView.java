package com.crossway.view;

import com.crossway.model.Board;
import com.crossway.model.PlayerColor;
import com.crossway.model.Position;

import java.util.Objects;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

public class ConsoleView implements GameView{
    private final Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    }
    public ConsoleView(Scanner scanner) {
        this.scanner = Objects.requireNonNull(scanner, "Scanner cannot be null");
    }

    @Override
    public void printRules() {
        String rules = """
                CROSSWAY RULES:
                - White (0) attempts to form a continuous chain connecting the North and South borders.
                - Black (X) attempts to form a continuous chain connecting the West and East borders.
                - Chains can connect orthogonally (up/down/left/right) or diagonally.
                - Crossway Constraint: A player is forbidden from placing a piece that completes a 2x2 square of alternating pieces (W-B / B-W), as this creates an illegal diagonal intersection.
                - Pie Rule: After Black makes the very first move, White has the option to swap colors and adopt Black's position.
                """;
        System.out.println(rules);
    }

    @Override
    public void printBoard(Board board) {
        System.out.println(board.toString());
    }

    @Override
    public boolean askPieRule() {
        System.out.println("Do you want to apply the Pie Rule? (y/n): ");
        String response = scanner.nextLine();
        return response.equalsIgnoreCase("y");
    }

    @Override
    public Position askForMove(PlayerColor currentTurn) {
        while (true) {
            String symbol = (currentTurn == PlayerColor.BLACK) ? "X" : "0";
            System.out.println("Turn of " + currentTurn + " (" + symbol + "). Enter your move (A1, J10):  ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.length() < 2 || input.length() > 3) {
                printMessage("Invalid format! Enter the letter (A-S) first, then the number (1-19)");
                continue;
            }

            char colChar = input.charAt(0);
            String rowString = input.substring(1);

            if (colChar < 'A' || colChar > 'S') {
                printMessage("Invalid column! Enter a letter between A and S");
                continue;
            }

            try {
                int rowNumber = Integer.parseInt(rowString);
                if (rowNumber < 1 || rowNumber > Board.BOARD_SIZE) {
                    printMessage("Invalid row! Enter a number between 1 and 19");
                    continue;
                }
                int row = rowNumber - 1;
                int column = colChar - 'A';

                return new Position(row, column);

            } catch (NumberFormatException e) {
                printMessage("Invalid format! Enter the letter (A-S) first, then the number (1-19)");
            }
        }
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printError(String error) {
        System.out.println("ERROR: " + error);
    }

    @Override
    public boolean isRestartRequested() {
        return false;
    }

    @Override
    public boolean askPlayAgain(PlayerColor winner) {
        System.out.println("The winner is " + winner + "!");
        System.out.print("Do you want to play again? (y/n): ");
        String choice = scanner.nextLine().trim().toLowerCase();
        return choice.equalsIgnoreCase("y");
    }

    @Override
    public void resetView() {
        System.out.println("\n GAME RESTARTED \n");
    }
}

