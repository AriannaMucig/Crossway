package com.crossway.controller;

import com.crossway.model.Game;
import com.crossway.model.Position;
import com.crossway.view.GameView;

public class GameController {
    private Game game;
    private final GameView view;

    public GameController(Game game, GameView view) {
        this.game = game;
        this.view = view;
    }

    public void start() {
        boolean keepPlaying = true;

        while (keepPlaying) {
            view.printRules();

            while (!game.getWinner().isPresent()) {
                view.printBoard(game.getBoard());

                if (game.getTurnsCount() == 2) {
                    if (view.askPieRule()) {
                        try {
                            game.applyPieRule();
                            view.printMessage("Pie Rule applied! Turn switched to Player " + game.getCurrentTurn());
                            continue;
                        } catch (IllegalStateException e) {
                            view.printError(e.getMessage());
                        }
                    }
                }

                Position move = view.askForMove(game.getCurrentTurn());

                if (view.isRestartRequested()) {
                    game.reset();
                    view.resetView();
                    view.printRules();
                    continue;
                }

                if (move != null) {
                    try {
                        game.playMove(move);
                    } catch (RuntimeException e) {
                        view.printError(e.getMessage());
                    }
                }
            }

            if (game.getWinner().isPresent()) {
                view.printBoard(game.getBoard());
                boolean playAgain = view.askPlayAgain(game.getWinner().get());

                if (playAgain) {
                    game.reset();
                    view.resetView();
                    view.printRules();
                } else {
                    keepPlaying = false;
                }
            }
        }
    }
}
