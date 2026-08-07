package com.crossway.model;

import java.util.Optional;

public class Game {
    private PlayerColor currentTurn;
    private final Board board;
    private int turnsCount;
    private Position firstMovePosition;
    private final WinningRule winningRule;

    public Game(WinningRule winningRule) {
        this.board = new Board();
        this.winningRule = winningRule;
        this.currentTurn = PlayerColor.BLACK;
        this.turnsCount = 1;
    }

    public Game() {
        this(new WinChecker());
    }
    public PlayerColor getCurrentTurn() {
        return currentTurn;
    }

    public int getTurnsCount() {
        return turnsCount;
    }

    public void playMove(Position pos) {
        if (winningRule.getWinner(board).isPresent()) {
            throw new IllegalStateException("Game is over! Winner: " + winningRule.getWinner(board).get());
        }
        PlayerColor color = currentTurn;
        if (turnsCount == 1) {
            firstMovePosition = pos;
        }
        board.placeStone(pos, color);
        currentTurn = color.opposite();
        turnsCount++;
    }

    public void applyPieRule() {
        if (turnsCount != 2 && currentTurn == PlayerColor.WHITE) {
            throw new IllegalStateException("Pie rule can only be applied on the second turn!");
        }

        board.changeStone(firstMovePosition, PlayerColor.WHITE);

        currentTurn = PlayerColor.BLACK;
        turnsCount++;
    }

    public Board getBoard() {
        return board;
    }

    public Optional<PlayerColor> getWinner() {
        return winningRule.getWinner(board);
    }

}
