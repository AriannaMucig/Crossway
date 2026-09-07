package com.crossway.view;

import com.crossway.model.Board;
import com.crossway.model.PlayerColor;
import com.crossway.model.Position;

public interface GameView {
    void printRules();
    void printBoard(Board board);
    boolean askPieRule();
    Position askForMove(PlayerColor playerColor);
    void printMessage(String message);
    void printError(String error);
    boolean askPlayAgain(PlayerColor winner);
    boolean isRestartRequested();
    void resetView();
}
