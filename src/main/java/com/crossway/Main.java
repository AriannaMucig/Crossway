package com.crossway;

import com.crossway.controller.GameController;
import com.crossway.model.Game;
import com.crossway.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        ConsoleView view = new ConsoleView();
        GameController controller = new GameController(game, view);

        controller.start();
    }
}