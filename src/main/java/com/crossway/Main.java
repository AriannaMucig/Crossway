package com.crossway;

import com.crossway.controller.GameController;
import com.crossway.model.Game;
import com.crossway.view.ConsoleView;
import com.crossway.view.GameView;
import com.crossway.view.GraphicalUserInterface;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        askMode(game, scanner);
    }

    public static void askMode(Game game, Scanner scanner) {
        boolean validChoice = false;

        while (!validChoice) {
            System.out.println("Do you want to play with the Command Line Interface (c) or with the Graphical User Interface (g)? (c/g)");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("c")) {
                validChoice = true;
                GameView view = new ConsoleView(scanner);
                GameController controller = new GameController(game, view);
                controller.start();

            } else if (response.equals("g")) {
                validChoice = true;
                SwingUtilities.invokeLater(() -> {
                    GraphicalUserInterface gui = new GraphicalUserInterface();
                    GameController controller = new GameController(game, gui);
                    new Thread(controller::start).start();
                });

            } else {
                System.out.println("Invalid choice. Please enter 'c' for CLI or 'g' for GUI.");
            }
        }
    }
}