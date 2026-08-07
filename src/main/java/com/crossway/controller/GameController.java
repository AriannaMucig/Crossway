package com.crossway.controller;

import com.crossway.model.Game;
import com.crossway.model.Position;
import com.crossway.view.ConsoleView;

public class GameController {
    private final Game game;
    private final ConsoleView view;

    public GameController(Game game, ConsoleView view) {
        this.game = game;
        this.view = view;
    }

    public void start() {
        view.printMessage("START");
        boolean gameOver = false;

        while (!gameOver) {
            view.printBoard(game.getBoard());
            if (game.getTurnsCount() == 2 && view.askPieRule()) {
                try {
                    game.applyPieRule();
                    view.printMessage("Pie Rule applied!");
                    continue;
                } catch (Exception e) {
                    view.printError(e.getMessage());
                }
            }

            try {
                Position move = view.askForMove(game.getCurrentTurn());
                game.playMove(move);

                if (game.getWinner().isPresent()) {
                    view.printBoard(game.getBoard());
                    view.printMessage("The winner is: " + game.getWinner().get());
                    gameOver = true;
                }
            } catch (Exception e) {
                view.printError(e.getMessage());
            }
        }
    }
}
