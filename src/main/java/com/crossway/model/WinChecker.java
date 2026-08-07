package com.crossway.model;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class WinChecker {

    public Optional<PlayerColor> getWinner(Board board) {
        if (checkWin(board, PlayerColor.WHITE)) {
            return Optional.of(PlayerColor.WHITE);
        }
        if (checkWin(board, PlayerColor.BLACK)) {
            return Optional.of(PlayerColor.BLACK);
        }
        return Optional.empty();
    }

    private boolean checkWin(Board board, PlayerColor color) {
        boolean[][] visited = new boolean[Board.BOARD_SIZE][Board.BOARD_SIZE];
        Queue<Position> queue = new LinkedList<>();

        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            Position startPos = (color == PlayerColor.WHITE) ? new Position(0, i) : new Position(i, 0);
            if (board.getStone(startPos) == color) {
                queue.add(startPos);
                visited[startPos.x()][startPos.y()] = true;
            }
        }

        while (!queue.isEmpty()) {
            Position current = queue.poll();
            if (color == PlayerColor.WHITE && current.x() == Board.BOARD_SIZE - 1) {
                return true;
            }
            if (color == PlayerColor.BLACK && current.y() == Board.BOARD_SIZE - 1) {
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