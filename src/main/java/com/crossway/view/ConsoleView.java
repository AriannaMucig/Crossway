package com.crossway.view;

import com.crossway.model.Board;
import com.crossway.model.PlayerColor;
import com.crossway.model.Position;

import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConsoleView {
    private final Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    }

    public void printBoard(Board board) {
        String header = "    " + IntStream.range(0, Board.BOARD_SIZE)
                .mapToObj(i -> String.valueOf((char) ('A' + i)))
                .collect(Collectors.joining("  "));

        System.out.println(header);

        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            System.out.printf("%2d ", row + 1);
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                Position pos = new Position(row, col);
                String symbol = board.getStone(pos)
                        .map(color -> color == PlayerColor.BLACK ? " X " : " 0 ")
                        .orElse(" * ");
                System.out.print(symbol);
            }
            System.out.printf(" %2d%n", row + 1);
        }

        System.out.println(header + "\n");
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printError(String error) {
        System.out.println("ERROR: " + error);
    }

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

    public boolean askPieRule() {
        System.out.println("Do you want to apply the Pie Rule? (y/n): ");
        String response = scanner.nextLine();
        return response.equalsIgnoreCase("y");
    }

    public void printRules() {
        String rules = """
                CROSSWAY
                
                Crossway is an abstract strategy board game played on a 19x19 grid between two players: Black (X) and White (O).
                
                Rules:
                - White attempts to form a continuous chain connecting the North and South borders.
                - Black attempts to form a continuous chain connecting the West and East borders.
                - Chains can connect orthogonally (up/down/left/right) or diagonally.
                - Crossway Constraint: A player is forbidden from placing a piece that completes a 2x2 square of alternating pieces (W-B / B-W), as this creates an illegal diagonal intersection.
                - Pie Rule: After Black makes the very first move, White has the option to swap colors and adopt Black's position.
                """;

        System.out.println(rules);
    }
}

