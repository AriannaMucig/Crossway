package com.crossway.model;

import java.util.LinkedList;
import java.util.Queue;

public class Game {
    private PlayerColor currentTurn;
    private Board board;
    private int turnsCount;
    private Position firstMovePosition;

    public Game() {
        this.board = new Board();
        this.currentTurn = PlayerColor.BLACK;
        this.turnsCount = 1;
    }

    public PlayerColor getCurrentTurn() {
        return currentTurn;
    }

    public void playMove(Position pos) {
        if (getWinner()!= null){
            throw new IllegalStateException("Game is over! Winner: " + getWinner());
        }
        PlayerColor color = currentTurn;
        if (turnsCount == 1) {
            firstMovePosition = pos;
        }
        board.placeStone(pos, color);
        currentTurn = (color == PlayerColor.BLACK) ? PlayerColor.WHITE : PlayerColor.BLACK;
    }

    public void applyPieRule() {
        if (turnsCount != 1 && currentTurn == PlayerColor.WHITE) {
            throw new IllegalStateException("Pie rule can only be applied on the second turn!");
        }

        board.changeStone(firstMovePosition, PlayerColor.WHITE);

        currentTurn = PlayerColor.BLACK;
        turnsCount++;
    }

    public Board getBoard() {
        return board;
    }

    public PlayerColor getWinner() {
        PlayerColor winner = null;
        if (checkWin(PlayerColor.WHITE)) {
            winner = PlayerColor.WHITE;
        }
        if (checkWin(PlayerColor.BLACK)) {
            winner = PlayerColor.BLACK;
        }
        return winner;
    }

    private boolean checkWin(PlayerColor color) {
        boolean[][] visited = new boolean[19][19];
        Queue<Position> queue = new LinkedList<>();

        for (int i = 0; i < 19; i++) {
            Position startPos = (color == PlayerColor.WHITE) ? new Position(0, i) : new Position(i, 0);
            if (board.getStone(startPos) == color) {
                queue.add(startPos);
                visited[startPos.x()][startPos.y()] = true;
            }
        }

        while (!queue.isEmpty()) {
            Position current = queue.poll();
            if (color == PlayerColor.WHITE && current.x() == 18) {
                return true;
            }
            if (color == PlayerColor.BLACK && current.y() == 18) {
                return true;
            }

            for (Position neighbor : board.getAdjacentPositions(current)) {
                if (!visited[neighbor.x()][neighbor.y()] && board.getStone(neighbor) == color) {
                    visited[neighbor.x()][neighbor.y()] = true;
                    queue.add(neighbor);
                }
            }
        }
        return false;
    }
}
