package com.crossway.view;

import com.crossway.model.Board;
import com.crossway.model.PlayerColor;
import com.crossway.model.Position;

import java.util.Scanner;
import java.nio.charset.StandardCharsets;

public class ConsoleView {
    private final Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    }

    public void printBoard(Board board) {
        System.out.println("    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O  P  Q  R  S");
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            System.out.printf("%2d ", i + 1);
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                if (board.getStone(new Position(i, j)) == PlayerColor.BLACK) {
                    System.out.print(" X ");
                } else if (board.getStone(new Position(i, j)) == PlayerColor.WHITE) {
                    System.out.print(" 0 ");
                } else {
                    System.out.print(" * ");
                }
            }
            System.out.printf(" %2d%n", i + 1);
        }
        System.out.println("    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O  P  Q  R  S\n");
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printError(String error) {
        System.out.println("ERROR: " + error);
    }

    public Position askForMove(PlayerColor currentTurn) {
        while (true) {
            System.out.println("Tun of " + currentTurn + ". Enter your move (A1, J10):  ");
            String input = scanner.next().trim().toUpperCase();

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
        String response = scanner.next();
        return response.equalsIgnoreCase("y");
    }
}

