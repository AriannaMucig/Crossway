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
        view.printRules();
        boolean gameOver = false;
        boolean boardNeedsRedraw = true;

        while (!gameOver) {
            if (boardNeedsRedraw) {
                view.printBoard(game.getBoard());
                boardNeedsRedraw = false;
            }

            if (game.getTurnsCount() == 2 && view.askPieRule()) {
                try {
                    game.applyPieRule();
                    view.printMessage("Pie Rule applied! Turn switched to Player " + game.getCurrentTurn());
                    boardNeedsRedraw = true;
                    continue;
                } catch (IllegalStateException e) {
                    view.printError(e.getMessage());
                    continue;
                }
            }

            try {
                Position move = view.askForMove(game.getCurrentTurn());
                game.playMove(move);

                if (game.getWinner().isPresent()) {
                    view.printBoard(game.getBoard());
                    view.printMessage(" The winner is: " + game.getWinner().get());
                    gameOver = true;
                } else {
                    boardNeedsRedraw = true;
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                view.printError(e.getMessage());
            }
        }
    }
}
